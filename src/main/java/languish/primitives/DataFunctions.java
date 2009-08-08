package languish.primitives;

import languish.base.DataFunction;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.base.DataFunction.SingleArgDataFunction;
import languish.base.DataFunction.TreeDataFunction;
import languish.base.DataFunction.TwoArgDataFunction;

public class DataFunctions {

  // BOOLEAN STUFF
  public static final DataFunction BRANCH = new SingleArgDataFunction() {
    @Override
    public Tuple applySingle(LObject arg) {
      return ((LBoolean) arg).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };
  public static final Tuple BRANCH_ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));
  public static final Tuple BRANCH_THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));

  public static final DataFunction AND = new TreeDataFunction() {
    @Override
    public LObject getNull() {
      return LBoolean.TRUE;
    }

    @Override
    public LObject reduce(LObject arg1, LObject arg2) {
      LBoolean a = (LBoolean) arg1;
      LBoolean b = (LBoolean) arg2;

      return LBoolean.of(a.booleanValue() && b.booleanValue());
    }
  };

  // INTEGER STUFF
  public static final DataFunction ADD = new TreeDataFunction() {
    @Override
    public LObject getNull() {
      return LInteger.of(0);
    }

    @Override
    public LObject reduce(LObject arg1, LObject arg2) {
      LInteger a = (LInteger) arg1;
      LInteger b = (LInteger) arg2;

      return LInteger.of(a.intValue() + b.intValue());
    }
  };

  // PARSE LITERALS
  public static final DataFunction PARSE_INT = new SingleArgDataFunction() {
    @Override
    public Tuple applySingle(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(LInteger.of(Integer.parseInt(symbol.stringValue())));
    }
  };

  public static final DataFunction EQUALS = new TwoArgDataFunction() {
    @Override
    public Tuple applyPair(LObject arg1, LObject arg2) {
      return Lambda.data(LBoolean.of(arg1.equals(arg2)));
    }
  };

  //
  // public static final DataFunction BUILTIN_GET = new DataFunction() {
  // @Override
  // public Tuple apply(LObject... obj) {
  // LSymbol symbol = (LSymbol) obj[0];
  //
  // return Lambda
  // .data(Builtins.valueOf(symbol.stringValue()).getExpression());
  // }
  // };

  private DataFunctions() {}
}
