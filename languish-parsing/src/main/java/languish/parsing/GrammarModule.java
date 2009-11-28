package languish.parsing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Tokens;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.pattern.Patterns;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.hjfreyer.util.Canonizer;
import com.hjfreyer.util.Pair;

public class GrammarModule {
  private final String rootRule;
  private final List<Pair<String, String>> tokenTypes;
  private final List<String> ignored;
  private final List<Production> rules;

  public GrammarModule(String rootRule, List<Pair<String, String>> tokenTypes,
      List<String> ignored, List<Production> rules) {
    super();
    this.rootRule = rootRule;
    this.tokenTypes = tokenTypes;
    this.ignored = ignored;
    this.rules = rules;
  }

  public Parser<ASTNode> getParser() {
    Parser<Fragment> lexer = Parsers.never();
    Parser<Void> delim = Parsers.never();

    for (Pair<String, String> tokenType : tokenTypes) {
      final String tag = Canonizer.canonize(tokenType.getFirst());
      final String regex = tokenType.getSecond();

      Parser<Fragment> token =
          Scanners.pattern(Patterns.regex(regex), tag).source().map(
              new Map<String, Fragment>() {
                public Fragment map(String from) {
                  return Tokens.fragment(from, tag);
                }
              });

      lexer = lexer.or(token);
    }

    for (String ignoredRegex : ignored) {
      Parser<Void> ignoredParser =
          Scanners.pattern(Patterns.regex(ignoredRegex), "IGNORED");

      delim = delim.or(ignoredParser);
    }

    return fromGrammarRules(rootRule, rules).from(lexer, delim);
  }

  private static Parser<ASTNode> fromGrammarRules(String rootRule,
      List<Production> rules) {
    List<String> types = new LinkedList<String>();
    Multimap<String, Production> grammarRules =
        Multimaps.newLinkedListMultimap();

    for (Production rule : rules) {
      String type = rule.getNonterminal();
      if (!grammarRules.keySet().contains(type)) {
        types.add(type);
      }
      grammarRules.put(type, rule);
    }

    HashMap<String, Parser<ASTNode>> parsers =
        new HashMap<String, Parser<ASTNode>>();
    HashMap<String, Parser.Reference<ASTNode>> parserRefs =
        new HashMap<String, Parser.Reference<ASTNode>>();

    for (String type : types) {
      parsers.put(type, Parsers.<ASTNode> never());
      parserRefs.put(type, Parser.<ASTNode> newReference());
    }

    for (String type : types) {
      for (final Production rule : grammarRules.get(type)) {
        Parser<ASTNode> prod =
            rule.getExpression().toParser(parserRefs).map(
                new Map<Object, ASTNode>() {
                  public ASTNode map(Object from) {
                    return null;
                    // return new ASTNode(rule.getName(), from);
                  }

                  @Override
                  public String toString() {
                    return rule.getName() + " wrapper";
                  }
                });
        parsers.put(type, parsers.get(type).or(prod));
      }
    }

    for (String type : types) {
      parserRefs.get(type).set(parsers.get(type));
    }

    return parsers.get(rootRule);
  }

  public List<Pair<String, String>> getTokenTypes() {
    return tokenTypes;
  }

  public List<String> getIgnored() {
    return ignored;
  }

  public List<Production> getRules() {
    return rules;
  }

  public String getRootRule() {
    return rootRule;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ignored == null) ? 0 : ignored.hashCode());
    result = prime * result + ((rules == null) ? 0 : rules.hashCode());
    result =
        prime * result + ((tokenTypes == null) ? 0 : tokenTypes.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GrammarModule other = (GrammarModule) obj;
    if (ignored == null) {
      if (other.ignored != null)
        return false;
    } else if (!ignored.equals(other.ignored))
      return false;
    if (rules == null) {
      if (other.rules != null)
        return false;
    } else if (!rules.equals(other.rules))
      return false;
    if (tokenTypes == null) {
      if (other.tokenTypes != null)
        return false;
    } else if (!tokenTypes.equals(other.tokenTypes))
      return false;
    return true;
  }

}
