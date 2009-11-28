package languish.parsing;

import java.util.HashMap;
import java.util.List;

import languish.base.Primitive;
import languish.util.PrimitiveTree;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Tokens;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.pattern.Patterns;

import com.google.common.collect.ImmutableList;
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

  public Parser<PrimitiveTree> getParser() {
    return getTokenLevelParser(rootRule, rules).from(getLexer(tokenTypes),
        getDelimiterParser(ignored));
  }

  private static Parser<Fragment> getLexer(List<Pair<String, String>> tokenTypes) {
    Parser<Fragment> lexer = Parsers.never();

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

    return lexer;
  }

  private static Parser<Void> getDelimiterParser(List<String> ignored) {
    Parser<Void> delim = Parsers.never();

    for (String ignoredRegex : ignored) {
      Parser<Void> ignoredParser =
          Scanners.pattern(Patterns.regex(ignoredRegex), "IGNORED");

      delim = delim.or(ignoredParser);
    }

    return delim;
  }

  private static Parser<PrimitiveTree> getTokenLevelParser(String rootRule,
      List<Production> rules) {

    Multimap<String, Production> rulesMap = Multimaps.newLinkedListMultimap();

    for (Production rule : rules) {
      rulesMap.put(rule.getNonterminal(), rule);
    }

    HashMap<String, Parser<PrimitiveTree>> nontermParsers =
        new HashMap<String, Parser<PrimitiveTree>>();
    HashMap<String, Parser.Reference<PrimitiveTree>> parserRefs =
        new HashMap<String, Parser.Reference<PrimitiveTree>>();

    for (String type : rulesMap.keySet()) {
      nontermParsers.put(type, Parsers.<PrimitiveTree> never());
      parserRefs.put(type, Parser.<PrimitiveTree> newReference());
    }

    for (String type : rulesMap.keySet()) {
      for (final Production production : rulesMap.get(type)) {
        Parser<PrimitiveTree> ruleParser =
            production.getExpression().toParser(parserRefs);
        Parser<PrimitiveTree> productionParser =
            ruleParser.map(wrapWithName(production.getName()));
        nontermParsers.put(type, nontermParsers.get(type).or(productionParser));
      }
    }

    for (String type : rulesMap.keySet()) {
      parserRefs.get(type).set(nontermParsers.get(type));
    }

    return nontermParsers.get(rootRule);
  }

  private static Map<PrimitiveTree, PrimitiveTree> wrapWithName(
      final String ruleName) {
    return new Map<PrimitiveTree, PrimitiveTree>() {
      public PrimitiveTree map(PrimitiveTree from) {
        Primitive name = new Primitive(ruleName);
        List<PrimitiveTree> wrapper =
            ImmutableList.of(PrimitiveTree.of(name), from);

        return PrimitiveTree.of(wrapper);
      }

      @Override
      public String toString() {
        return ruleName + " wrapper";
      }
    };
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
