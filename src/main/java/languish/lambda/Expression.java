package languish.lambda;

public abstract class Expression {

  public static enum Type {
    ABSTRACTION,
    APPLICATION,
    WRAPPER,
    REFERENCE,
    NATIVE_FUNC
  }

  public abstract Type getType();
}