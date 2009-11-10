package languish.primitives;

import languish.lambda.Primitive;

public abstract class AbstractPrimitive implements Primitive {

  public abstract Object getJavaObject();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result
            + ((getJavaObject() == null) ? 0 : getJavaObject().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Primitive other = (Primitive) obj;
    if (getJavaObject() == null) {
      if (other.getJavaObject() != null)
        return false;
    } else if (!getJavaObject().equals(other.getJavaObject()))
      return false;
    return true;
  }
}
