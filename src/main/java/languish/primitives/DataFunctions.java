package languish.primitives;

import languish.base.LObject;
import languish.base.Lambda;
import languish.base.PrimitiveFunction;
import languish.base.Tuple;
import languish.base.PrimitiveFunction.SingleArgDataFunction;
import languish.base.PrimitiveFunction.TwoArgDataFunction;
import languish.interpreter.Builtins;

public class DataFunctions {

  // BOOLEAN STUFF
  public static final PrimitiveFunction BRANCH = new SingleArgDataFunction() {
    @Override
    public Tuple apply(LObject arg) {
      return ((LBoolean) arg).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };
  public static final Tuple BRANCH_ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));
  public static final Tuple BRANCH_THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));

  public static final PrimitiveFunction AND = new TwoArgDataFunction() {
    @Override
    public Tuple apply(LObject arg1, LObject arg2) {
      LBoolean a = (LBoolean) arg1;
      LBoolean b = (LBoolean) arg2;

      return Lambda.data(LBoolean.of(a.booleanValue() && b.booleanValue()));
    }
  };

  public static final PrimitiveFunction NOT = new SingleArgDataFunction() {
    @Override
    public Tuple apply(LObject arg) {
      LBoolean a = (LBoolean) arg;

      return Lambda.data(LBoolean.of(!a.booleanValue()));
    }
  };

  // INTEGER STUFF
  public static final PrimitiveFunction ADD = new TwoArgDataFunction() {
    @Override
    public Tuple apply(LObject arg1, LObject arg2) {
      LInteger a = (LInteger) arg1;
      LInteger b = (LInteger) arg2;

      return Lambda.data(LInteger.of(a.intValue() + b.intValue()));
    }
  };

  public static final PrimitiveFunction MULTIPLY = new TwoArgDataFunction() {
    @Override
    public Tuple apply(LObject arg1, LObject arg2) {
      LInteger a = (LInteger) arg1;
      LInteger b = (LInteger) arg2;

      return Lambda.data(LInteger.of(a.intValue() * b.intValue()));
    }
  };

  // PARSE LITERALS
  public static final PrimitiveFunction PARSE_INT =
      new SingleArgDataFunction() {
        @Override
        public Tuple apply(LObject obj) {
          LSymbol symbol = (LSymbol) obj;

          return Lambda.data(LInteger
              .of(Integer.parseInt(symbol.stringValue())));
        }
      };

  public static final PrimitiveFunction DATA_EQUALS = new PrimitiveFunction() {
    @Override
    public Tuple apply(Tuple tuple) {
      if (tuple.getFirst() != Lambda.CONS) {
        throw new IllegalArgumentException("argument must be cons");
      }

      Tuple arg1 = (Tuple) tuple.getSecond();
      Tuple arg2 = (Tuple) tuple.getThird();

      return Lambda.data(LBoolean.of(arg1.equals(arg2)));
    }
  };

  public static final PrimitiveFunction BUILTIN_GET =
      new SingleArgDataFunction() {
        @Override
        public Tuple apply(LObject arg) {
          LSymbol symbol = (LSymbol) arg;

          return Lambda.data(Builtins.valueOf(symbol.stringValue())
              .getExpression());
        }
      };

  // DEBUGGING

  public static final PrimitiveFunction PRINT = new PrimitiveFunction() {
    @Override
    public Tuple apply(Tuple arg) {
      System.out.println(arg);
      return arg;
    }
  };

  // public static final PrimitiveFunction IS_NULL = new PrimitiveFunction() {
  // @Override
  // public Tuple apply(Tuple tuple) {
  // boolean isNull =
  // tuple.getFirst() == Lambda.DATA
  // && tuple.getSecond().equals(Tuple.of());
  //
  // return Lambda.data(LBoolean.of(isNull));
  // }
  // };

  private DataFunctions() {
  }
}
