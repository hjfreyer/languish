package languish.prim.data;

import languish.lambda.DataFunction;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;

public class LParsers {
  private LParsers() {}

  public static final DataFunction PARSE_INT = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(LInteger.of(Integer.parseInt(symbol.stringValue())));
    }
  };
}
