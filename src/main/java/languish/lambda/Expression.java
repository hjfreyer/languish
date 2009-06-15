package languish.lambda;

import languish.prim.data.LObject;

public abstract class Expression extends LObject {

  public static enum Type {
    ABSTRACTION,
    APPLICATION,
    WRAPPER,
    REFERENCE,
    NATIVE_FUNC
  }

  public abstract Type getType();
}