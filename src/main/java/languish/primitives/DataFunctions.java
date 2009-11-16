package languish.primitives;

import languish.interpreter.Builtins;
import languish.lambda.LObject;
import languish.lambda.NativeFunction;
import languish.lambda.Term;
import languish.lambda.NativeFunction.SingleArgDataFunction;
import languish.lambda.NativeFunction.TwoArgDataFunction;
import languish.util.Lambda;

public class DataFunctions {

  // BOOLEAN STUFF
  public static final NativeFunction BRANCH = new SingleArgDataFunction() {
    @Override
    public Term apply(LObject arg) {
      return ((LBoolean) arg).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };
  public static final Term BRANCH_ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));
  public static final Term BRANCH_THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));

  public static final NativeFunction AND = new TwoArgDataFunction() {
    @Override
    public Term apply(LObject arg1, LObject arg2) {
      LBoolean a = (LBoolean) arg1;
      LBoolean b = (LBoolean) arg2;

      return Lambda.primitive(LBoolean.of(a.booleanValue() && b.booleanValue()));
    }
  };

  public static final NativeFunction NOT = new SingleArgDataFunction() {
    @Override
    public Term apply(LObject arg) {
      LBoolean a = (LBoolean) arg;

      return Lambda.primitive(LBoolean.of(!a.booleanValue()));
    }
  };

  // INTEGER STUFF
  public static final NativeFunction ADD = new TwoArgDataFunction() {
    @Override
    public Term apply(LObject arg1, LObject arg2) {
      LInteger a = (LInteger) arg1;
      LInteger b = (LInteger) arg2;

      return Lambda.primitive(LInteger.of(a.intValue() + b.intValue()));
    }
  };

  public static final NativeFunction MULTIPLY = new TwoArgDataFunction() {
    @Override
    public Term apply(LObject arg1, LObject arg2) {
      LInteger a = (LInteger) arg1;
      LInteger b = (LInteger) arg2;

      return Lambda.primitive(LInteger.of(a.intValue() * b.intValue()));
    }
  };

  // PARSE LITERALS
  public static final NativeFunction PARSE_INT =
      new SingleArgDataFunction() {
        @Override
        public Term apply(LObject obj) {
          LSymbol symbol = (LSymbol) obj;

          return Lambda.primitive(LInteger
              .of(Integer.parseInt(symbol.stringValue())));
        }
      };

  public static final NativeFunction DATA_EQUALS = new NativeFunction() {
    @Override
    public Term apply(Term tuple) {
      if (tuple.getFirst() != languish.util.CONS) {
        throw new IllegalArgumentException("argument must be cons");
      }

      Term arg1 = (Term) tuple.getSecond();
      Term arg2 = (Term) tuple.getThird();

      return Lambda.primitive(LBoolean.of(arg1.equals(arg2)));
    }
  };

  public static final NativeFunction BUILTIN_GET =
      new SingleArgDataFunction() {
        @Override
        public Term apply(LObject arg) {
          LSymbol symbol = (LSymbol) arg;

          return Lambda.primitive(Builtins.valueOf(symbol.stringValue())
              .getExpression());
        }
      };

  // DEBUGGING

  public static final NativeFunction PRINT = new NativeFunction() {
    @Override
    public Term apply(Term arg) {
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
