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
      final LInteger intVal1 = (LInteger) obj;

      return Lambda.prim(new DataFunction() {
        @Override
        public Tuple apply(LObject obj) {
          final LInteger intVal2 = (LInteger) obj;

          return Lambda.data(LInteger.of(intVal1.intValue()
              + intVal2.intValue()));
        }
      });
    }
  };
}
