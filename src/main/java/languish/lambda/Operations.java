package languish.lambda;

import languish.error.AlreadyReducedError;

public class Operations {

  public static final Operation ABS = new Operation() {

    public Term reduce(Term term) {
      throw new AlreadyReducedError(term);
    }

    public boolean isReduced(Term term) {
      return true;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      Term subterm = (Term) term.getFirst();

      return subterm.replaceAllReferencesToParam(id + 1, with);
    }
  };

  public static final Operation APP = new Operation() {
    public Term reduce(Term term) {
      Term func = (Term) term.getFirst();
      Term arg = (Term) term.getSecond();

      if (func.getOperation() != ABS) {
        return new Term(ABS, func.reduce(), arg);
      }

      return func.replaceAllReferencesToParam(1, arg);
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
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

      if (ref_id == id) {
        return with;
      } else if (ref_id > id) {
        return new Term(REF, ref_id - 1, Term.NULL);
      } else {
        return term;
      }
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
  };

  public static final Operation CONS = new Operation() {

    public Term reduce(Term cons) {
      Term car = (Term) cons.getFirst();
      Term cdr = (Term) cons.getSecond();

      if (!car.isReduced()) {
        return new Term(CONS, car.reduce(), cdr);
      } else if (!cdr.isReduced()) {
        return new Term(CONS, car, cdr.reduce());
      } else {
        throw new AlreadyReducedError(cons);
      }
    }

    public boolean isReduced(Term cons) {
      Term car = (Term) cons.getFirst();
      Term cdr = (Term) cons.getSecond();

      return car.isReduced() && cdr.isReduced();
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }
  };

  public static final Operation CAR = new Operation() {
    public Term reduce(Term car) {
      Term arg = (Term) car.getFirst();

      if (arg.getOperation() != CONS) {
        return new Term(CAR, arg.reduce(), Term.NULL);
      }

      return (Term) arg.getSecond();
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }
  };

  public static final Operation CDR = new Operation() {
    public Term reduce(Term cdr) {
      Term arg = (Term) cdr.getFirst();

      if (arg.getOperation() != CONS) {
        return new Term(CDR, arg.reduce(), Term.NULL);
      }

      return (Term) arg.getSecond();
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }
  };

  public static final Operation NATIVE_APPLY = new Operation() {

    public Term reduce(Term prim) {
      Term func = (Term) prim.getFirst();
      Term arg = (Term) prim.getSecond();

      if (func.getOperation() != PRIMITIVE) {
        return new Term(NATIVE_APPLY, func.reduce(), arg);
      } else if (!arg.isReduced()) {
        return new Term(NATIVE_APPLY, func, arg.reduce());
      }

      NativeFunction primFunc = (NativeFunction) func.getFirst();
      return primFunc.apply(arg);
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
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

  };

  public static final Operation BRANCH = new Operation() {

    public Term reduce(Term term) {
      Term condition = (Term) term.getFirst();

      if (!condition.isReduced()) {
        return new Term(BRANCH, condition.reduce(), Term.NULL);
      }

      final Term THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));
      final Term ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));

      return (condition.getOperation() != NOOP) ? THEN : ELSE;
    }

    public boolean isReduced(Term term) {
      return false;
    }

    public Term replaceAllReferencesToParam(Term term, int id, Term with) {
      return propagateReplaceReference(term, id, with);
    }

  };

  public static Term propagateReplaceReference(Term term, int id, Term with) {
    Term firstTerm = (Term) term.getFirst();
    Term secondTerm = (Term) term.getSecond();

    return new Term(term.getOperation(), firstTerm.replaceAllReferencesToParam(
        id, with), secondTerm.replaceAllReferencesToParam(id, with));
  }

  private Operations() {
  }
}
