package languish.lambda;

import languish.lambda.error.IllegalReductionError;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;

public class Lambda {
  private Lambda() {}

  public static LObject reduce(LObject exp) {
    Tuple tuple = (Tuple) exp;

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

    return op.reduceOnce(arg);
  }

  public static final Operation ABS = new IrreducibleOperation("ABS");

  public static final Operation APP = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple app) {
      Tuple func = (Tuple) app.get(0);
      Tuple arg = (Tuple) app.get(1);

      if (func.getFirst() != ABS) {
        app.getContents()[0] = reduceTupleOnce(func);
        return new Tuple(APP, app);
      }

      Tuple abs = (Tuple) func.getSecond();

      return replaceAllReferencesToParam((Tuple) abs.getFirst(), 1, arg);
    }
  };

  public static final Operation DATA = new IrreducibleOperation("RES");

  public static final Operation GET = new Operation() {
    @Override
    public Tuple reduceOnce(Tuple tuple) {
      if (tuple.getSecond() != TUPLE) {
        tuple.setSecond(reduceTupleOnce(tuple.getSecond()));
        return tuple;
      }

      LInteger i = (LInteger) tuple.getFirst();
      Tuple t = (Tuple) tuple.getSecond();

      return (Tuple) t.getContents()[i.intValue()];
    }
  };

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

    if (op == DATA) {
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

}
