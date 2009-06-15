package languish.lambda;

import languish.prim.data.LObject;

public abstract class NativeFunction extends Expression {

  private final String name;

  public NativeFunction(String name) {
    this.name = name;
  }

  public abstract Expression apply(LObject obj);

  @Override
  public final Type getType() {
    return Type.NATIVE_FUNC;
  }

  public final String getName() {
    return name;
  }

  @Override
  public final String toString() {
    return "(~" + name + "~)";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    NativeFunction other = (NativeFunction) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

}
