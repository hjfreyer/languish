package languish.base;

import languish.base.error.AlreadyReducedError;
import languish.base.error.IllegalPrimitiveFunctionApplicationError;
import languish.base.error.IllegalReductionError;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;

public class Lambda {
  private Lambda() {
  }

  public static LObject reduceToDataValue(LObject exp) {
    Tuple reduced = reduce((Tuple) exp);

    if (reduced.getFirst() != DATA) {
      throw new AssertionError("Argument was not data");
    }

    return reduced.getSecond();
  }

  public static Tuple reduce(Tuple exp) {
    Tuple tuple = exp.deepClone();

    while (!isPrimitive(tuple)) {
      tuple = reduceTupleOnce(tuple);
    }

    return tuple;
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

      if (func.getFirst() != ABS) {
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

  public static final Operation CONS = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      Tuple car = (Tuple) tuple.getSecond();
      Tuple cdr = (Tuple) tuple.getThird();

      if (isReducible(car)) {
        tuple.setSecond(reduceTupleOnce(car));
        return tuple;
      } else if (isReducible(cdr)) {
        tuple.setThird(reduceTupleOnce(cdr));
        return tuple;
      } else {
        throw new AlreadyReducedError(tuple);
      }
    }
  };

  public static final Operation IS_PRIMITIVE = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      Tuple arg = (Tuple) tuple.getSecond();

      if (isReducible(arg)) {
        tuple.setSecond(reduceTupleOnce(arg));
        return tuple;
      }

      return data(isPrimitive(arg) ? LBoolean.TRUE : LBoolean.FALSE);
    }
  };

  public static final Operation PRIM = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple prim) {
      Tuple func = (Tuple) prim.getSecond();
      Tuple arg = (Tuple) prim.getThird();

      if (func.getFirst() != DATA) {
        prim.setSecond(reduceTupleOnce(func));
        return prim;
      }
      PrimitiveFunction primFunc = (PrimitiveFunction) func.getSecond();

      if (isReducible(arg)) {
        prim.setThird(reduceTupleOnce(arg));
        return prim;
      } else if (isPrimitive(arg)) {
        return primFunc.applyWithCopy(arg);
      } else {
        throw new IllegalPrimitiveFunctionApplicationError(arg);
      }
    }
  };

  public static final Operation REF = new IrreducibleOperation();

  public static boolean isReducible(Tuple tuple) {
    Operation op = (Operation) tuple.getFirst();

    if (op == APP || op == CAR || op == CDR || op == PRIM || op == IS_PRIMITIVE) {
      return true;
    } else if (op == DATA || op == REF || op == ABS) {
      return false;
    } else if (op == CONS) {
      return isReducible((Tuple) tuple.getSecond())
          || isReducible((Tuple) tuple.getThird());
    } else {
      throw new AssertionError();
    }
  }

  // Is the function reduced all the way, with only CONS and DATA left?
  public static boolean isPrimitive(Tuple tuple) {
    Operation op = (Operation) tuple.getFirst();

    if (op == DATA) {
      return true;
    } else if (op == CONS) {
      return isPrimitive((Tuple) tuple.getSecond())
          && isPrimitive((Tuple) tuple.getThird());
    } else if (op == REF || op == ABS || op == CAR || op == CDR || op == APP
        || op == PRIM || op == IS_PRIMITIVE) {
      return false;
    } else {
      throw new AssertionError();
    }
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
      return id == ((LInteger) exp.getSecond()).intValue()
          ? with.deepClone()
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
    if (op == CAR || op == CDR || op == IS_PRIMITIVE) {
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

  public static Tuple is_prim(Tuple arg) {
    return Tuple.of(IS_PRIMITIVE, arg);
  }

  public static Tuple ref(int i) {
    return Tuple.of(REF, LInteger.of(i));
  }

  public static Tuple prim(PrimitiveFunction func, Tuple arg) {
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
