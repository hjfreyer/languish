package languish.base;

import languish.primitives.LInteger;

public class Lambda {
  private Lambda() {}

  public static LObject reduce(LObject exp) {
    Tuple tuple = (Tuple) exp.deepClone();

    while (true) {
      if (tuple.getFirst() != DATA) {
        tuple = reduceTupleOnce(tuple);
      } else {
        return tuple.getSecond();
      }
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
      LInteger index = (LInteger) get.getSecond();
      Tuple arg = (Tuple) get.getThird();

      if (arg.getFirst() != CONS && arg.getFirst() != DATA) {
        get.setThird(reduceTupleOnce(arg));
        return get;
      }

      if (arg.getFirst() == CONS) {
        return (Tuple) arg.get(index.intValue());
      }

      if (arg.getFirst() == DATA) {
        return data(((Tuple) arg.getSecond()).get(index.intValue() - 1));
      }

      throw new AssertionError();
    }
  };

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

  public static final Operation CONS = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      for (int i = 1; i < tuple.size(); i++) {
        Tuple child = (Tuple) tuple.get(i);

        if (child.getFirst() != DATA) {
          tuple.set(i, reduceTupleOnce(child));
          return tuple;
        }
      }

      LObject[] reduced = new LObject[tuple.size() - 1];

      for (int i = 1; i < tuple.size(); i++) {
        Tuple child = (Tuple) tuple.get(i);
        // Since all children are reduced, first element is DATA

        reduced[i - 1] = child.getSecond();
      }

      return data(Tuple.of(reduced));
    }
  };

  public static final Operation PRIM = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple prim) {
      DataFunction func = (DataFunction) prim.getSecond();
      Tuple argument = (Tuple) prim.getThird();

      if (argument.getFirst() != DATA) {
        prim.setThird(reduceTupleOnce(argument));
        return prim;
      }

      return func.apply(argument.getSecond()).deepClone();
    }
  };

  public static final Operation REF = new IrreducibleOperation("REF");

  private static class IrreducibleOperation extends Operation {
    private final String name;

    public IrreducibleOperation(String name) {
      this.name = name;
    }

    @Override
    public Tuple reduceOnce(Tuple tuple) {
      throw new IllegalReductionError(name + " cannot be reduced");
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
    if (op == CONS) {
      for (int i = 1; i < exp.size(); i++) {
        exp.set(i, replaceAllReferencesToParam((Tuple) exp.get(i), id, with));
      }
      return exp;
    }
    if (op == APP) {
      exp.setSecond( //
          replaceAllReferencesToParam((Tuple) exp.getSecond(), id, with));
      exp.setThird( // 
          replaceAllReferencesToParam((Tuple) exp.getThird(), id, with));
      return exp;
    }
    if (op == GET || op == PRIM) {
      exp.setThird( // 
          replaceAllReferencesToParam((Tuple) exp.getThird(), id, with));
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
    return Tuple.of(PRIM, func, arg);
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

  public static Tuple get(int i, Tuple cons) {
    return Tuple.of(GET, LInteger.of(i), cons);
  }

}
