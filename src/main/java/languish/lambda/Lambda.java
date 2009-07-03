package languish.lambda;

import languish.lambda.error.IllegalReductionError;
import languish.prim.data.LInteger;

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

  public static Tuple reduceTupleOnce(LObject tuple) {
    Tuple t = (Tuple) tuple;
    Operation op = (Operation) t.getFirst();
    Tuple arg = (Tuple) t.getSecond();

    Tuple result = op.reduceOnce(arg);

    return result;
  }

  public static final Operation ABS = new IrreducibleOperation("ABS");

  public static final Operation APP = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple app) {
      Tuple func = (Tuple) app.getFirst();
      Tuple arg = (Tuple) app.getSecond();

      LObject funcType = func.getFirst();
      LObject argType = arg.getFirst();

      if (funcType != ABS && funcType != PRIM) {
        app.setFirst(reduceTupleOnce(func));
        return Tuple.of(APP, app);
      }

      if (funcType == PRIM) {
        if (argType != DATA) {
          app.setSecond(reduceTupleOnce(arg));
          return Tuple.of(APP, app);
        }

        DataFunction dataFunc = (DataFunction) func.getSecond();
        return dataFunc.apply(arg.getSecond()).deepClone();
      }

      if (funcType == ABS) {
        Tuple abs = (Tuple) func.getSecond();

        return replaceAllReferencesToParam((Tuple) abs.getFirst(), 1, arg);
      }

      throw new AssertionError();
    }
  };

  public static final Operation DATA = new IrreducibleOperation("RES");

  public static final Operation GET = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple get) {
      LInteger i = (LInteger) get.getFirst();
      Tuple tuple = (Tuple) get.getSecond();

      if (tuple.getFirst() != TUPLE) {
        get.setSecond(reduceTupleOnce(tuple));
        return Tuple.of(GET, get);
      }

      Tuple contents = (Tuple) tuple.getSecond();

      return (Tuple) contents.get(i.intValue());
    }
  };

  public static final Operation PRIM = new IrreducibleOperation("PRIM");

  public static final Operation REF = new IrreducibleOperation("REF");

  public static final Operation TUPLE = new IrreducibleOperation("TUPLE");

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

    if (op == DATA || op == PRIM) {
      return exp;
    }
    if (op == REF) {
      return id == ((LInteger) exp.getSecond()).intValue() ? with : exp;
    }
    if (op == ABS) {
      id++;
    }

    Tuple arg = (Tuple) exp.getSecond();

    for (int i = 0; i < arg.size(); i++) {
      arg.getContents()[i] =
          replaceAllReferencesToParam((Tuple) arg.getContents()[i], id, with);
    }

    return exp;
  }

  public static Tuple abs(Tuple exp) {
    return Tuple.of(ABS, Tuple.of(exp));
  }

  public static Tuple app(LObject func, LObject arg) {
    return Tuple.of(APP, Tuple.of(func, arg));
  }

  public static Tuple data(LObject obj) {
    return Tuple.of(DATA, obj);
  }

  public static Tuple ref(int i) {
    return Tuple.of(REF, LInteger.of(i));
  }

  public static Tuple prim(DataFunction func) {
    return Tuple.of(PRIM, func);
  }

}
