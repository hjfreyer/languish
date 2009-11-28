package languish.parsing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import languish.base.Primitive;
import languish.util.PrimitiveTree;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Expression {
  public enum Op {
    SEQ, NONTERM, TERM,
  }

  private final Op op;
  private final Object content;

  public Expression(Op op, Object content) {
    super();
    this.op = op;
    if (content instanceof List<?>) {
      this.content = ImmutableList.copyOf((List<?>) content);
    } else {
      this.content = content;
    }
  }

  public static Expression seq(Expression... content) {
    return new Expression(Op.SEQ, ImmutableList.copyOf(Arrays.asList(content)));
  }

  public static Expression nonterm(String content) {
    return new Expression(Op.NONTERM, content);
  }

  public static Expression term(String content) {
    return new Expression(Op.TERM, content);
  }

  //
  // private static Map<PrimitiveTree, PrimitiveTree> singleton() {
  //
  // return new Map<PrimitiveTree, PrimitiveTree>() {
  // @Override
  // public PrimitiveTree map(PrimitiveTree from) {
  // return PrimitiveTree.of(ImmutableList.of(from));
  //
  // }
  // };
  // }

  private static <T> Map<T, Primitive> toPrimitive() {
    return new Map<T, Primitive>() {
      @Override
      public Primitive map(T from) {
        return new Primitive(from);
      }
    };
  }

  private static Map<List<PrimitiveTree>, PrimitiveTree> listToPrimitiveTree() {
    return new Map<List<PrimitiveTree>, PrimitiveTree>() {
      @Override
      public PrimitiveTree map(List<PrimitiveTree> from) {
        return PrimitiveTree.of(from);
      }
    };
  }

  private static Map<Primitive, PrimitiveTree> primitiveToPrimitiveTree() {
    return new Map<Primitive, PrimitiveTree>() {
      @Override
      public PrimitiveTree map(Primitive from) {
        return PrimitiveTree.of(from);
      }
    };
  }

  @SuppressWarnings("unchecked")
  public Parser<PrimitiveTree> toParser(
      final HashMap<String, Parser.Reference<PrimitiveTree>> parserRefs) {
    switch (op) {
      case NONTERM :
        final String nontermName = (String) content;
        return parserRefs.get(nontermName).lazy();
      case TERM :
        final String tag = (String) content;
        return Terminals.fragment(tag.intern()).map(toPrimitive()).map(
            primitiveToPrimitiveTree());
      case SEQ :
        List<Expression> subExpressions = (List<Expression>) content;
        List<Parser<PrimitiveTree>> childParsers =
            Lists.transform(
                subExpressions,
                new Function<Expression, Parser<PrimitiveTree>>() {
                  public Parser<PrimitiveTree> apply(Expression from) {
                    return from.toParser(parserRefs);
                  }
                });
        return Parsers.list(childParsers).map(listToPrimitiveTree());
    }

    throw new AssertionError();
  }

  @SuppressWarnings("unchecked")
  public static Expression fromList(List<?> list) {
    if (list.size() != 2) {
      throw new IllegalArgumentException();
    }

    Op op = Op.valueOf((String) list.get(0));

    switch (op) {
      case NONTERM :
      case TERM :
        return new Expression(op, list.get(1));
      case SEQ :
        List<Expression> children = new LinkedList<Expression>();
        for (List<?> child : (List<List<?>>) list.get(1)) {
          children.add(Expression.fromList(child));
        }
        return new Expression(op, children);
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
    Expression other = (Expression) obj;
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