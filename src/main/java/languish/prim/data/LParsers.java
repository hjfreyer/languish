package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;

public class LParsers {
  private LParsers() {}

  public static final NativeFunction PARSE_INT =
      new NativeFunction("PARSE_INT") {
        @Override
        public Expression apply(LObject obj) {
          LSymbol symbol = (LSymbol) obj;

          return Wrapper
              .of(LInteger.of(Integer.parseInt(symbol.stringValue())));
        }
      };
}
