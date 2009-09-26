package languish.parsing;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ParserTree {
  enum Op {
    SEQ, NONTERM, TERM,
  }

  private final Op op;
  private final List<ParserTree> children;
  private final String value;

  public ParserTree(Op op, List<ParserTree> children, String value) {
    super();
    this.op = op;
    this.children = children;
    this.value = value;
  }

  public Parser<?> toParser(
      final HashMap<String, Parser.Reference<ASTNode>> parserRefs) {
    switch (op) {
      case NONTERM :
        return parserRefs.get(value).lazy();
      case TERM :
        final String tag = value;
        return Terminals.fragment(tag).map(new Map<String, ASTNode>() {
          public ASTNode map(String from) {
            return new ASTNode(tag, from);
          }

          @Override
          public String toString() {
            return "token wrapper";
          }
        });
      case SEQ :
        final List<Parser<?>> childParsers =
            Lists.transform(children, new Function<ParserTree, Parser<?>>() {
              public Parser<?> apply(ParserTree from) {
                return from.toParser(parserRefs);
              }
            });
        return Parsers.list(childParsers);
    }

    return null;
  }

  public Op getOp() {
    return op;
  }

  public List<ParserTree> getChildren() {
    return children;
  }

  public String getValue() {
    return value;
  }

}