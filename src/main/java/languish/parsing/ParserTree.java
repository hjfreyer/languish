package languish.parsing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Canonizer;

public class ParserTree {
  public enum Op {
    SEQ, NONTERM, TERM,
  }

  private final Op op;
  private final Object content;

  public ParserTree(Op op, Object content) {
    super();
    this.op = op;
    if (content instanceof List<?>) {
      this.content = ImmutableList.copyOf((List<?>) content);
    } else {
      this.content = content;
    }
  }

  @SuppressWarnings("unchecked")
  public Parser<?> toParser(
      final HashMap<String, Parser.Reference<ASTNode>> parserRefs) {
    switch (op) {
      case NONTERM :
        return parserRefs.get(content).lazy();
      case TERM :
        final String tag = (String) content;
        return Terminals.fragment(Canonizer.canonize(tag)).map(
            new Map<String, ASTNode>() {
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
            Lists.transform((List<ParserTree>) content,
                new Function<ParserTree, Parser<?>>() {
                  public Parser<?> apply(ParserTree from) {
                    return from.toParser(parserRefs);
                  }
                });
        return Parsers.list(childParsers);
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  public static ParserTree fromList(List<?> list) {
    if (list.size() != 2) {
      throw new IllegalArgumentException();
    }

    Op op = Op.valueOf((String) list.get(0));

    switch (op) {
      case NONTERM :
      case TERM :
        return new ParserTree(op, list.get(1));
      case SEQ :
        List<ParserTree> children = new LinkedList<ParserTree>();
        for (List<?> child : (List<List<?>>) list.get(1)) {
          children.add(ParserTree.fromList(child));
        }
        return new ParserTree(op, children);
    }
    throw new AssertionError();
  }

  public Op getOp() {
    return op;
  }

  public Object getContent() {
    return content;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + ((op == null) ? 0 : op.hashCode());
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
    ParserTree other = (ParserTree) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (op == null) {
      if (other.op != null)
        return false;
    } else if (!op.equals(other.op))
      return false;
    return true;
  }

}