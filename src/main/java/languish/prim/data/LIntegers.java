package languish.prim.data;

import languish.lambda.DataFunction;

public class LIntegers {
  private LIntegers() {}

  public static final DataFunction ADD = new DataFunction() {
    @Override
    public LObject apply(LObject obj) {
      final LInteger intVal1 = (LInteger) obj;
      return null;
    }
  };
}
