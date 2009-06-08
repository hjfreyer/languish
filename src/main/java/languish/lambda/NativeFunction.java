package languish.lambda;

import languish.prim.data.LObject;

public abstract class NativeFunction extends Expression {

  public abstract Expression apply(LObject obj);

  private final String name;
  private final boolean canonizable;

  public NativeFunction(String name) {
    this(name, false);
  }

  public NativeFunction(String name, boolean canonizable) {
    this.name = name;
    this.canonizable = canonizable;
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
  public final Expression replaceAllReferencesToParam(int id, Expression with) {
    return this;
  }

  public final String getName() {
    return name;
  }

  public final boolean isCanonizable() {
    return canonizable;
  }

  @Override
  public final String toString() {
    return "(~" + name + "~)";
  }
}
