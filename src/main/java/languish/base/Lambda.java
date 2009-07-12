package languish.base;

import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
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

  public static final Operation ABS = new IrreducibleOperation("ABS");

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

  public static final Operation DATA = new IrreducibleOperation("DATA");

  public static final Operation GET = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple get) {
      Tuple index = (Tuple) get.getSecond();
      Tuple arg = (Tuple) get.getThird();

      if (index.getFirst() != DATA) {
        get.setSecond(reduceTupleOnce(index));
        return get;
      }

      if (arg.getFirst() != CONS) {
        get.setThird(reduceTupleOnce(arg));
        return get;
      }

      LInteger indexInt = (LInteger) index.getSecond();

      return (Tuple) arg.get(indexInt.intValue());
    }
  };

  // public static final Operation GET = new Operation() {
  // @Override
  // public Tuple reduceOnce(Tuple get) {
  // LInteger index = (LInteger) get.getSecond();
  // Tuple arg = (Tuple) get.getThird();
  //
  // if (arg.getFirst() != CONS && arg.getFirst() != DATA) {
  // get.setThird(reduceTupleOnce(arg));
  // return get;
  // }
  //
  // if (arg.getFirst() == CONS) {
  // return (Tuple) arg.get(index.intValue());
  // }
  //
  // if (arg.getFirst() == DATA) {
  // return data(((Tuple) arg.getSecond()).get(index.intValue() - 1));
  // }
  //
  // throw new AssertionError();
  // }
  // };

  // public static final Operation GET = new Operation() {
  // @Override
  // public Tuple reduceOnce(Tuple get) {
  // LInteger i = (LInteger) get.getFirst();
  // Tuple tuple = (Tuple) get.getSecond();
  //
  // if (tuple.getFirst() != TUPLE) {
  // get.setSecond(reduceTupleOnce(tuple));
  // return Tuple.of(GET, get);
  // }
  //
  // Tuple contents = (Tuple) tuple.getSecond();
  //
  // return (Tuple) contents.get(i.intValue());
  // }
  // };

  public static final Operation CONS = new IrreducibleOperation("CONS");

  // public static final Operation CONS = new Operation() {
  // @Override
  // public Tuple reduceOnce(Tuple tuple) {
  // for (int i = 1; i < tuple.size(); i++) {
  // Tuple child = (Tuple) tuple.get(i);
  //
  // if (child.getFirst() != DATA) {
  // tuple.set(i, reduceTupleOnce(child));
  // return tuple;
  // }
  // }
  //
  // LObject[] reduced = new LObject[tuple.size() - 1];
  //
  // for (int i = 1; i < tuple.size(); i++) {
  // Tuple child = (Tuple) tuple.get(i);
  // // Since all children are reduced, first element is DATA
  //
  // reduced[i - 1] = child.getSecond();
  // }
  //
  // return data(Tuple.of(reduced));
  // }
  // };

  public static final Operation PRIM = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple prim) {
      // Reduce children
      for (int i = 1; i < prim.size(); i++) {
        Tuple child = (Tuple) prim.get(i);

        if (child.getFirst() != DATA) {
          prim.set(i, reduceTupleOnce(child));
          return prim;
        }
      }

      DataFunction func = (DataFunction) ((Tuple) prim.getSecond()).getSecond();
      LObject[] args = new LObject[prim.size() - 2];

      for (int i = 2; i < prim.size(); i++) {
        Tuple arg = (Tuple) prim.get(i);
        // Since all children are reduced, first element is "DATA"
        args[i - 2] = arg.getSecond();
      }

      return func.apply(args).deepClone();
    }
  };

  public static final Operation EQUALS = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      Tuple expA = (Tuple) tuple.getSecond();
      Tuple expB = (Tuple) tuple.getThird();

      Operation opA = (Operation) expA.getFirst();
      Operation opB = (Operation) expB.getFirst();

      if (isReducible(opA)) {
        tuple.setSecond(reduceTupleOnce(expA));
        return tuple;
      }
      if (isReducible(opB)) {
        tuple.setThird(reduceTupleOnce(expB));
        return tuple;
      }
      if (opA != opB) {
        return Lambda.data(LBoolean.FALSE);
      }
      if (opA == ABS) {
        tuple.setSecond(expA.getSecond());
        tuple.setThird(expB.getSecond());
        return tuple;
      }
      if (opA == DATA || opA == REF) {
        return Lambda.data(LBoolean.of(expA.getSecond()
            .equals(expB.getSecond())));
      }
      if (opA == CONS) {
        if (expA.size() != expB.size()) {
          return Lambda.data(LBoolean.FALSE);
        }

        Tuple[] equalities = new Tuple[expA.size() - 1];

        for (int i = 1; i < expA.size(); i++) {
          equalities[i - 1] = Tuple.of(EQUALS, expA.get(i), expB.get(i));
        }

        return DataFunctions.andChain(equalities);
      }

      throw new AssertionError();
    }
  };

  public static final Operation REF = new IrreducibleOperation("REF");

  public static boolean isReducible(Operation op) {
    return op == APP || op == GET || op == PRIM || op == EQUALS;
  }

  private static class IrreducibleOperation extends Operation {
    private final String name;

    public IrreducibleOperation(String name) {
      this.name = name;
    }

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
    if (op == CONS || op == PRIM || op == APP || op == EQUALS || op == GET) {
      for (int i = 1; i < exp.size(); i++) {
        exp.set(i, replaceAllReferencesToParam((Tuple) exp.get(i), id, with));
      }
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

  public static Tuple prim(DataFunction func, Tuple... args) {
    LObject[] tuple = new LObject[args.length + 2];

    tuple[0] = PRIM;
    tuple[1] = data(func);

    for (int i = 0; i < args.length; i++) {
      tuple[i + 2] = args[i];
    }

    return Tuple.of(tuple);
  }

  public static Tuple eq(Tuple a, Tuple b) {
    return Tuple.of(EQUALS, a, b);
  }

  public static Tuple cons() {
    return Tuple.of(CONS);
  }

  public static Tuple cons(LObject obj1) {
    return Tuple.of(CONS, obj1);
  }

  public static Tuple cons(LObject obj1, LObject obj2) {
    return Tuple.of(CONS, obj1, obj2);
  }

  public static Tuple cons(LObject obj1, LObject obj2, LObject obj3) {
    return Tuple.of(CONS, obj1, obj2, obj3);
  }

  public static Tuple cons(LObject obj1, LObject obj2, LObject obj3,
      LObject obj4) {
    return Tuple.of(CONS, obj1, obj2, obj3, obj4);
  }

  public static Tuple get(int i, Tuple cons) {
    return Tuple.of(GET, data(LInteger.of(i)), cons);
  }

}
