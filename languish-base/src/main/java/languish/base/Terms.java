package languish.base;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Terms {

  public static final Term TRUE = Terms.abs(Terms.abs(Terms.ref(2)));

  public static final Term FALSE = Terms.abs(Terms.abs(Terms.ref(1)));

  public static Term abs(Term exp) {
    return new Term(Operations.ABS, exp, Term.NULL);
  }

  public static Term app(Term func, Term arg) {
    return new Term(Operations.APP, func, arg);
  }

  public static Term primitive(Primitive prim) {
    return new Term(Operations.PRIMITIVE, prim, Term.NULL);
  }

  public static Term primObj(Object obj) {
    return new Term(Operations.PRIMITIVE, new Primitive(obj), Term.NULL);
  }

  public static Term ref(int i) {
    return new Term(Operations.REF, i, Term.NULL);
  }

  public static Term nativeApply(final NativeFunction func, Term arg) {
    return new Term(Operations.NATIVE_APPLY, Terms
        .primitive(new Primitive(func)), arg);
  }

  public static Term print(Term term) {
    return new Term(Operations.PRINT, term, Term.NULL);
  }

  public static Term cons(Term obj1, Term obj2) {
    return Terms.abs(Terms.app(Terms.app(Terms.ref(1), obj1), obj2));
  }

  public static Term car(Term obj) {
    return Terms.app(obj, Terms.TRUE);
  }

  public static Term cdr(Term obj) {
    return Terms.app(obj, Terms.FALSE);
  }

  public static Term equals(Term first, Term second) {
    return new Term(Operations.EQUALS, first, second);
  }

  public static Term branch(Term condition, Term thenTerm, Term elseTerm) {
    return Terms.app(Terms.app(condition, thenTerm), elseTerm);
  }

  private Terms() {
  }

  public static Term convertJavaObjectToTerm(Tree<Primitive> obj) {
    if (obj.isList()) {
      List<Tree<Primitive>> list = obj.asList();

      Term result = Term.NULL;
      for (int i = list.size() - 1; i >= 0; i--) {
        result = Terms.cons(Terms.convertJavaObjectToTerm(list.get(i)), result);
      }

      return result;
    } else if (obj.isLeaf()) {
      return Terms.primitive(obj.asLeaf());
    }

    throw new AssertionError();
  }

  public static Tree<Primitive> convertTermToJavaObject(Term term) {
    term = term.reduceCompletely();

    Operation op = term.getOperation();
    if (op == Operations.NOOP) {
      return Tree.<Primitive> empty();
    }

    if (op == Operations.PRIMITIVE) {
      return Tree.leaf(((Primitive) term.getFirst()));
    }

    if (op == Operations.ABS) {
      Tree<Primitive> car = Terms.convertTermToJavaObject(Terms.car(term));
      List<Tree<Primitive>> cdr = Terms
          .convertTermToJavaObject(Terms.cdr(term)).asList();

      List<Tree<Primitive>> result = Lists.newLinkedList();

      result.add(car);
      result.addAll(cdr);

      return Tree.inode(ImmutableList.copyOf(result));
    }

    throw new IllegalArgumentException("term is not in a convertible state: "
        + term);
  }

  public static Tree<String> convertTermToAst(Term term) {
    return null;
  }

  public static Term compileAstToTerm(Tree<String> ast) {
    if (ast.isLeaf() && ast.asLeaf().equals("NULL")) {
      return Term.NULL;
    }
    String opName = ast.asList().get(0).asLeaf();
    Operation op = Operations.fromName(opName);
    Tree<String> first = ast.asList().get(1);
    Tree<String> second = ast.asList().get(2);
    if (op == Operations.PRIMITIVE) {
      return Terms.primObj(first.asLeaf());
    } else if (op == Operations.REF) {
      return Terms.ref(Integer.parseInt(first.asLeaf()));
    }
    return new Term(op, Terms.compileAstToTerm(first), Terms
        .compileAstToTerm(second));
  }

  public static Term compileTermAstToTerm(Term astTerm) {
    Tree<Primitive> astPrim = Terms.convertTermToJavaObject(astTerm);

    Tree<String> ast = Trees.transform(astPrim,
        new Function<Primitive, String>() {
          @Override
          public String apply(Primitive from) {
            return from.toString();
          }
        });
    return Terms.compileAstToTerm(ast);
  }
}
