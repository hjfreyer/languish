package languish.parsing;

import java.util.HashMap;
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
import com.hjfreyer.util.Pair;

public class LParser {

  private final List<Pair<String, String>> tokenTypes;
  private final List<String> ignored;
  private final List<GrammarRule> rules;

  public LParser(List<Pair<String, String>> tokenTypes, List<String> ignored,
      List<GrammarRule> rules) {
    this.tokenTypes = tokenTypes;
    this.ignored = ignored;
    this.rules = rules;
  }

  public Parser<ASTNode> getParser() {
    Parser<Fragment> lexer = Parsers.never();
    Parser<Void> delim = Parsers.never();

    for (Pair<String, String> tokenType : tokenTypes) {
      final String regex = tokenType.getFirst();
      final String tag = tokenType.getSecond();

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
      Parser<Void> ignored =
          Scanners.pattern(Patterns.regex(ignoredRegex), "IGNORED");

      delim = delim.or(ignored);
    }

    return fromGrammarRules(rules).from(lexer, delim);
  }

  public static Parser<ASTNode> fromGrammarRules(List<GrammarRule> rules) {
    Multimap<String, GrammarRule> grammarRules =
        Multimaps.newLinkedListMultimap();

    for (GrammarRule rule : rules) {
      grammarRules.put(rule.getType(), rule);
    }

    HashMap<String, Parser<ASTNode>> parsers =
        new HashMap<String, Parser<ASTNode>>();
    HashMap<String, Parser.Reference<ASTNode>> parserRefs =
        new HashMap<String, Parser.Reference<ASTNode>>();

    for (String type : grammarRules.keySet()) {
      parsers.put(type, Parsers.<ASTNode> never());
      parserRefs.put(type, Parser.<ASTNode> newReference());
    }

    for (String type : grammarRules.keySet()) {
      for (final GrammarRule rule : grammarRules.get(type)) {
        Parser<ASTNode> prod =
            rule.getTree().toParser(parserRefs).map(new Map<Object, ASTNode>() {
              public ASTNode map(Object from) {
                return new ASTNode(rule.getName(), from);
              }

              @Override
              public String toString() {
                return rule.getName() + " wrapper";
              }
            });
        parsers.put(type, parsers.get(type).or(prod));
      }
    }

    for (String type : grammarRules.keySet()) {
      parserRefs.get(type).set(parsers.get(type));
    }

    return parsers.get("ROOT");
  }

}
