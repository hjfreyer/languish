package languish.primitives;

import languish.base.DataFunction;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.Builtins;

public class DataFunctions {

  // BOOLEAN STUFF
  public static final DataFunction BRANCH = new DataFunction() {
    @Override
    public Tuple apply(LObject... args) {
      return ((LBoolean) args[0]).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };
  public static final Tuple BRANCH_ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));
  public static final Tuple BRANCH_THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));

  public static final DataFunction AND = new DataFunction() {
    @Override
    public Tuple apply(LObject... args) {
      LBoolean a = (LBoolean) args[0];
      LBoolean b = (LBoolean) args[1];

      return Lambda.data(LBoolean.of(a.booleanValue() && b.booleanValue()));
    }
  };

  public static Tuple andChain(Tuple... exps) {
    Tuple result = exps[0];

    for (int i = 1; i < exps.length; i++) {
      result = Lambda.prim(AND, exps[i], result);
    }

    return result;
  }

  // INTEGER STUFF
  public static final DataFunction ADD = new DataFunction() {
    @Override
    public Tuple apply(LObject... args) {
      LInteger a = (LInteger) args[0];
      LInteger b = (LInteger) args[1];

      return Lambda.data(LInteger.of(a.intValue() + b.intValue()));
    }
  };

  // PARSE LITERALS
  public static final DataFunction PARSE_INT = new DataFunction() {
    @Override
    public Tuple apply(LObject... obj) {
      LSymbol symbol = (LSymbol) obj[0];

      return Lambda.data(LInteger.of(Integer.parseInt(symbol.stringValue())));
    }
  };

  public static final DataFunction BUILTIN_GET = new DataFunction() {
    @Override
    public Tuple apply(LObject... obj) {
      LSymbol symbol = (LSymbol) obj[0];

      return Lambda
          .data(Builtins.valueOf(symbol.stringValue()).getExpression());
    }
  };

  private DataFunctions() {}
}
