package languish.parsing;

import java.util.LinkedList;
import java.util.List;

import languish.base.PrimitiveFunction;
import languish.base.Tuple;
import languish.base.Util;

import com.hjfreyer.util.Pair;

public class LParsers {
  public static final PrimitiveFunction PARSE_TEXT = new PrimitiveFunction() {
    @Override
    public Tuple apply(Tuple arg) {
      List<?> argList = (List<?>) Util.convertPrimitiveToJava(arg);

      List<?> grammar = (List<?>) argList.get(0);
      String text = (String) argList.get(1);

      LParser parser = fromListStructure(grammar);
      ASTNode result = parser.getParser().parse(text);

      return Util.convertJavaToPrimitive(result.toListStructure());
    }
  };

  @SuppressWarnings("unchecked")
  public static LParser fromListStructure(List<?> struct) {
    if (struct.size() != 4) {
      throw new IllegalArgumentException();
    }

    String root = (String) struct.get(0);
    List<List<String>> tokenTypesList = (List<List<String>>) struct.get(1);
    List<String> ignored = (List<String>) struct.get(2);
    List<List<?>> grammarRulesList = (List<List<?>>) struct.get(3);

    // TokenTypes
    List<Pair<String, String>> tokenTypes =
        new LinkedList<Pair<String, String>>();
    for (List<String> tokenType : tokenTypesList) {
      if (tokenType.size() != 2) {
        throw new IllegalArgumentException();
      }
      tokenTypes.add(Pair.of(tokenType.get(0), tokenType.get(1)));
    }

    // Grammar Rules
    List<GrammarRule> rules = new LinkedList<GrammarRule>();
    for (List<?> grammarRuleList : grammarRulesList) {
      if (grammarRuleList.size() != 3) {
        throw new IllegalArgumentException();
      }

      String type = (String) grammarRuleList.get(0);
      String name = (String) grammarRuleList.get(1);
      ParserTree tree = ParserTree.fromList((List<?>) grammarRuleList.get(2));

      rules.add(new GrammarRule(type, name, tree));
    }

    return new LParser(root, tokenTypes, ignored, rules);
  }
}
