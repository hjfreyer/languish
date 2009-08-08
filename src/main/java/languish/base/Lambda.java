package languish.base;

import languish.primitives.LInteger;

public class Lambda {
  private Lambda() {}

  public static LObject reduce(LObject exp) {
    Tuple tuple = (Tuple) exp.deepClone();

    while (true) {
      if (tuple.getFirst() == DATA) {
        return tuple.getSecond();
      }

      tuple = reduceTupleOnce(tuple);
    }
  }

  private static Tuple reduceTupleOnce(LObject tuple) {
    Tuple t = (Tuple) tuple;
    Operation op = (Operation) t.getFirst();

    Tuple result = op.reduceOnce(t);

    return result;
  }

  public static final Operation ABS = new IrreducibleOperation();

  public static final Operation APP = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple app) {
      Tuple func = (Tuple) app.getSecond();
      Tuple arg = (Tuple) app.getThird();

      LObject funcType = func.getFirst();

      if (funcType != ABS) {
        app.setSecond(reduceTupleOnce(func));
        return app;
      }

      return replaceAllReferencesToParam((Tuple) func.getSecond(), 1, arg);
    }
  };

  public static final Operation DATA = new IrreducibleOperation();

  public static final Operation CAR = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple car) {
      Tuple arg = (Tuple) car.getSecond();

      if (arg.getFirst() != CONS) {
        car.setSecond(reduceTupleOnce(arg));
        return car;
      }

      return (Tuple) arg.getSecond();
    }
  };

  public static final Operation CDR = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple cdr) {
      Tuple arg = (Tuple) cdr.getSecond();

      if (arg.getFirst() != CONS) {
        cdr.setSecond(reduceTupleOnce(arg));
        return cdr;
      }

      return (Tuple) arg.getThird();
    }
  };

  public static final Operation CONS = new IrreducibleOperation();

  public static final Operation PRIM = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple prim) {
      Tuple func = (Tuple) prim.getSecond();
      Tuple arg = (Tuple) prim.getThird();

      if (func.getFirst() != DATA) {
        prim.setSecond(reduceTupleOnce(func));
        return prim;
      }

      if (isReducible(arg.getFirst())) {
        prim.setThird(reduceTupleOnce(arg));
        return prim;
      }

      DataFunction dataFunc = (DataFunction) func.getSecond();

      if (arg.getFirst() == DATA) {
        return dataFunc.applySingle(arg.getSecond()).deepClone();
      }

      if (arg.getFirst() == CONS) {
        Tuple car = (Tuple) arg.getSecond();
        Tuple cdr = (Tuple) arg.getThird();

        if (isReducible(car.getFirst())) {
          arg.setSecond(reduceTupleOnce(car));
          return prim;
        }
        if (isReducible(cdr.getFirst())) {
          arg.setThird(reduceTupleOnce(cdr));
          return prim;
        }
        // Recurse until all values are DATA
        if (car.getFirst() != DATA) {
          arg.setSecond(prim(dataFunc, car));
          return prim;
        }
        if (cdr.getFirst() != DATA) {
          arg.setThird(prim(dataFunc, cdr));
          return prim;
        }
        return dataFunc.applyPair(car.getSecond(), cdr.getSecond()).deepClone();
      }

      throw new AssertionError();
    }
  };

  public static final Operation REF = new IrreducibleOperation();

  public static boolean isReducible(LObject op) {
    return op == APP || op == CAR || op == CDR || op == PRIM;
  }

  private static class IrreducibleOperation extends Operation {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      throw new IllegalReductionError("Expression cannot be reduced " + tuple);
    }
  }

  private static Tuple replaceAllReferencesToParam(Tuple exp, int id, Tuple with) {
    Operation op = (Operation) exp.getFirst();

    if (op == DATA) {
      return exp;
    }
    if (op == REF) {
      return id == ((LInteger) exp.getSecond()).intValue() ? with.deepClone()
          : exp;
    }
    if (op == ABS) {
      exp.setSecond(replaceAllReferencesToParam((Tuple) exp.getSecond(),
          id + 1, with));
      return exp;
    }
    if (op == CONS || op == APP || op == PRIM) {
      exp.setSecond( //
          replaceAllReferencesToParam((Tuple) exp.getSecond(), id, with));
      exp.setThird( //
          replaceAllReferencesToParam((Tuple) exp.getThird(), id, with));
      return exp;
    }
    if (op == CAR || op == CDR) {
      exp.setSecond( //
          replaceAllReferencesToParam((Tuple) exp.getSecond(), id, with));
      return exp;
    }
    throw new AssertionError();
  }

  public static Tuple abs(Tuple exp) {
    return Tuple.of(ABS, exp);
  }

  public static Tuple app(Tuple func, Tuple arg) {
    return Tuple.of(APP, func, arg);
  }

  public static Tuple data(LObject obj) {
    return Tuple.of(DATA, obj);
  }

  public static Tuple ref(int i) {
    return Tuple.of(REF, LInteger.of(i));
  }

  public static Tuple prim(DataFunction func, Tuple arg) {
    return Tuple.of(PRIM, data(func), arg);
  }

  public static Tuple cons(Tuple obj1, Tuple obj2) {
    return Tuple.of(CONS, obj1, obj2);
  }

  public static Tuple car(Tuple obj) {
    return Tuple.of(CAR, obj);
  }

  public static Tuple cdr(Tuple obj) {
    return Tuple.of(CDR, obj);
  }

}
