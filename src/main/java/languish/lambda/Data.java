package languish.lambda;

import languish.prim.data.LObject;

public class Data {
  public static Tuple of(LObject obj) {
    return Tuple.of(Lambda.DATA, obj);
  }
}