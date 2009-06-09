package languish.lambda;

import languish.prim.data.LObject;

public abstract class Expression {

  public static enum Type {
    ABSTRACTION,
    APPLICATION,
    WRAPPER,
    REFERENCE,
    // PRIM_CALL,
    // BRANCH,
    // EVAL,
    NATIVE_FUNC
  }

  public abstract Type getType();
}