package languish.lambda;

import languish.prim.data.LObject;

public abstract class NativeFunction extends Expression {

  public abstract Expression apply(LObject obj);

  private final String name;

  public NativeFunction(String name) {
    this.name = name;
  }

  @Override
  public final Type getType() {
    return Type.NATIVE_FUNC;
  }

  @Override
  public final Expression reduceOnce() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Expression replaceAllReferencesToParam(int id, Expression with) {
    return this;
  }

  public final String getName() {
    return name;
  }

  @Override
  public final String toString() {
    return "(~" + name + "~)";
  }

}
