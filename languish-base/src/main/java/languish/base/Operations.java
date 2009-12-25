package languish.base;

import languish.base.error.AlreadyReducedError;
import languish.util.PrimitiveTree;

public class Operations {

  public static Operation fromName(String name) {
    if (name.equals("ABS")) {
      return ABS;
    } else if (name.equals("APP")) {
      return APP;
    } else if (name.equals("EQUALS")) {
      return EQUALS;
    } else if (name.equals("NATIVE_APPLY")) {
      return NATIVE_APPLY;
    } else if (name.equals("NOOP")) {
      return NOOP;
    } else if (name.equals("PRIMITIVE")) {
      return PRIMITIVE;
    } else if (name.equals("REF")) {
      return REF;
    } else if (name.equals("PRINT")) {
      return PRINT;
    } else {
      throw new IllegalArgumentException("No operation called " + name);
    }
  }

  public static final Operation ABS = new Operation() {

    public Term reduce(Term term) {
      throw new AlreadyReducedError(term);
    }

    public boolean isReduced(Term term) {
      return true;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      Term subterm = (Term) term.getFirst();
      return Terms.abs(subterm.replaceAllReferencesToParam(id + 1, with));
    }

    @Override
    public String toString() {
      return "ABS";
    }
  };

  public static final Operation APP = new Operation() {
    public Term reduce(Term term) {
      Term func = (Term) term.getFirst();
      Term arg = (Term) term.getSecond();

      if (func.getOperation() != ABS) {
        return new Term(APP, func.reduce(), arg);
      }

      assert !hasReferencesGreaterThan(arg, 0);

      Term absExp = (Term) func.getFirst();

      return absExp.replaceAllReferencesToParam(1, arg);
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }

    @Override
    public String toString() {
      return "APP";
    }
  };

  public static final Operation REF = new Operation() {
    public Term reduce(Term term) {
      throw new AlreadyReducedError(term);
    }

    public boolean isReduced(Term term) {
      return true;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      int ref_id = (Integer) term.getFirst();

      return (ref_id == id) ? with : term;
    }

    @Override
    public String toString() {
      return "REF";
    }
  };

  public static final Operation PRIMITIVE = new Operation() {
    public Term reduce(Term term) {
      throw new AlreadyReducedError(term);
    }

    public boolean isReduced(Term term) {
      return true;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return term;
    }

    @Override
    public String toString() {
      return "PRIMITIVE";
    }
  };

  public static final Operation NATIVE_APPLY = new Operation() {

    public Term reduce(Term nativeApply) {
      Term func = (Term) nativeApply.getFirst();
      Term arg = (Term) nativeApply.getSecond();

      if (func.getOperation() != PRIMITIVE) {
        return new Term(NATIVE_APPLY, func.reduce(), arg);
      }

      if (!arg.isReduced()) {
        return new Term(NATIVE_APPLY, func, arg.reduce());
      }

      Primitive funcPrimitive = (Primitive) func.getFirst();
      NativeFunction nativeFunc =
          (NativeFunction) funcPrimitive.getJavaObject();

      PrimitiveTree argObject = Terms.convertTermToJavaObject(arg);
      return Terms.convertJavaObjectToTerm(nativeFunc.apply(argObject));
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }

    @Override
    public String toString() {
      return "NATIVE_APPLY";
    }
  };

  public static final Operation NOOP = new Operation() {
    public Term reduce(Term term) {
      throw new AlreadyReducedError(term);
    }

    public boolean isReduced(Term term) {
      return true;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return term;
    }

    @Override
    public String toString() {
      return "NOOP";
    }
  };

  public static final Operation EQUALS = new Operation() {
    public Term reduce(Term term) {
      Term t1 = (Term) term.getFirst();
      Term t2 = (Term) term.getSecond();

      if (!t1.isReduced()) {
        return new Term(EQUALS, t1.reduce(), t2);
      }

      if (!t2.isReduced()) {
        return new Term(EQUALS, t1, t2.reduce());
      }

      if (t1.getOperation() != t2.getOperation()) {
        return Terms.FALSE;
      }

      if (t1.getOperation() == NOOP) {
        return Terms.TRUE;
      }

      if (t1.getOperation() == PRIMITIVE) {
        return t1.getFirst().equals(t2.getFirst()) ? Terms.TRUE : Terms.FALSE;
      }

      if (t1.getOperation() == REF) {
        int r1 = (Integer) t1.getFirst();
        int r2 = (Integer) t2.getFirst();

        return r1 == r2 ? Terms.TRUE : Terms.FALSE;
      }

      if (t1.getOperation() == ABS) {
        Term cond1 = new Term(EQUALS, Terms.car(t1), Terms.car(t2));
        Term cond2 = new Term(EQUALS, Terms.cdr(t1), Terms.cdr(t2));

        return Terms.branch(
            cond1,
            Terms.branch(cond2, Terms.TRUE, Terms.FALSE),
            Terms.FALSE);
      }

      throw new AssertionError("Invalid operation");
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }

    @Override
    public String toString() {
      return "EQUALS";
    }
  };

  public static final Operation PRINT = new Operation() {
    public Term reduce(Term term) {
      Term subterm = (Term) term.getFirst();

      if (!subterm.isReduced()) {
        return Terms.print(subterm.reduce());
      }

      System.out.println(subterm);
      return subterm;
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }

    @Override
    public String toString() {
      return "PRINT";
    }
  };

  public static Term propagateReplaceReference(Term term, int id, Term with) {
    Term firstTerm = (Term) term.getFirst();
    Term secondTerm = (Term) term.getSecond();

    return new Term(term.getOperation(), firstTerm.replaceAllReferencesToParam(
        id,
        with), secondTerm.replaceAllReferencesToParam(id, with));
  }

  public static boolean hasReferencesGreaterThan(Term term, int max) {
    Operation op = term.getOperation();

    if (op == REF) {
      return ((Integer) term.getFirst()) > max;
    }

    if (op == NOOP || op == PRIMITIVE) {
      return false;
    }

    if (op == ABS) {
      return hasReferencesGreaterThan((Term) term.getFirst(), max + 1);
    }

    if (op == APP || op == EQUALS || op == NATIVE_APPLY || op == PRINT) {
      return hasReferencesGreaterThan((Term) term.getFirst(), max)
          || hasReferencesGreaterThan((Term) term.getSecond(), max);
    }

    throw new AssertionError("Invalid operation");
  }

  private Operations() {
  }
}
