package languish.lambda;

import languish.prim.data.LObject;

public final class Application {

  public static Tuple of(LObject func, LObject arg) {
    return Tuple.of(Lambda.APP, Tuple.of(func, arg));
  }
}
