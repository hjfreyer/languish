package languish.prim.data;

import languish.base.DataFunction;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;

public class DataFunctions {

  // BOOLEAN STUFF
  public static final DataFunction BRANCH = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      return ((LBoolean) obj).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };
  public static final Tuple BRANCH_ELSE = Lambda.abs(Lambda.abs(Lambda.ref(1)));
  public static final Tuple BRANCH_THEN = Lambda.abs(Lambda.abs(Lambda.ref(2)));

  // INTEGER STUFF
  public static final DataFunction ADD = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      Tuple pair = (Tuple) obj;

      LInteger a = (LInteger) pair.getFirst();
      LInteger b = (LInteger) pair.getSecond();

      return Lambda.data(LInteger.of(a.intValue() + b.intValue()));
    }
  };

  // PARSE LITERALS
  public static final DataFunction PARSE_INT = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(LInteger.of(Integer.parseInt(symbol.stringValue())));
    }
  };

  private DataFunctions() {}
}
