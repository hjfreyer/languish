package languish.prim.data;

import languish.lambda.Data;
import languish.lambda.DataFunction;

public class LParsers {
  private LParsers() {}

  public static final DataFunction PARSE_INT = new DataFunction() {
    @Override
    public LObject apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Data.of(LInteger.of(Integer.parseInt(symbol.stringValue())));
    }
  };
}
