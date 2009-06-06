package languish.lambda;

import languish.lambda.error.IllegalFreeVariableError;
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

  public abstract Expression reduceOnce();

  public abstract Expression replaceAllReferencesToParam(int id, Expression with);

  public final LObject reduceCompletely() {
    Expression exp = this;

    while (true) {
      switch (exp.getType()) {
      case APPLICATION:
        // case BRANCH:
        // case PRIM_CALL:
        // case EVAL:
        exp = exp.reduceOnce();
        break;

      case NATIVE_FUNC:
      case ABSTRACTION:
      case REFERENCE:
        throw new IllegalFreeVariableError(exp
            + " cannot be reduced to wrapper");

      case WRAPPER:
        return ((Wrapper) exp).getContents();

      }
    }
  }
}