package languish.base;

import java.util.List;

import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Terms {

  public static final Term TRUE = abs(abs(ref(2)));

  public static final Term FALSE = abs(abs(ref(1)));

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
    return new Term(Operations.NATIVE_APPLY, primitive(new Primitive(func)),
        arg);
  }

  public static Term print(Term term) {
    return new Term(Operations.PRINT, term, Term.NULL);
  }

  public static Term cons(Term obj1, Term obj2) {
    return abs(app(app(ref(1), obj1), obj2));
  }

  public static Term car(Term obj) {
    return app(obj, TRUE);
  }

  public static Term cdr(Term obj) {
    return app(obj, FALSE);
  }

  public static Term branch(Term condition, Term thenTerm, Term elseTerm) {
    return app(app(condition, thenTerm), elseTerm);
  }

  private Terms() {
  }

  public static Term convertJavaObjectToTerm(PrimitiveTree obj) {
    if (obj.isList()) {
      List<PrimitiveTree> list = obj.asList();

      Term result = Term.NULL;
      for (int i = list.size() - 1; i >= 0; i--) {
        result = Terms.cons(convertJavaObjectToTerm(list.get(i)), result);
      }

      return result;
    } else if (obj.isPrimitive()) {
      return primitive(obj.asPrimitive());
    }

    throw new AssertionError();
  }

  public static PrimitiveTree convertTermToJavaObject(Term term) {
    term = term.reduceCompletely();

    Operation op = term.getOperation();
    if (op == Operations.NOOP) {
      return PrimitiveTree.of(ImmutableList.<PrimitiveTree> of());
    }

    if (op == Operations.PRIMITIVE) {
      return PrimitiveTree.of((Primitive) term.getFirst());
    }

    if (op == Operations.ABS) {
      PrimitiveTree car = convertTermToJavaObject(Terms.car(term));
      List<PrimitiveTree> cdr =
          convertTermToJavaObject(Terms.cdr(term)).asList();

      List<PrimitiveTree> result = Lists.newLinkedList();

      result.add(car);
      result.addAll(cdr);

      return PrimitiveTree.of(ImmutableList.copyOf(result));
    }

    throw new IllegalArgumentException("term is not in a convertible state: "
        + term);
  }
}
