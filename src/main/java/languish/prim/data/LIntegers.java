package languish.prim.data;

import languish.lambda.DataFunction;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;

public class LIntegers {
  private LIntegers() {}

  public static final DataFunction ADD = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      Tuple pair = (Tuple) obj;

      LInteger a = (LInteger) pair.getFirst();
      LInteger b = (LInteger) pair.getSecond();

      return Lambda.data(LInteger.of(a.intValue() + b.intValue()));
    }
  };
}
