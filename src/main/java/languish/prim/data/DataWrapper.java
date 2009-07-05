package languish.prim.data;

import languish.base.LObject;

public abstract class DataWrapper extends LObject {

  public abstract Object getJavaValue();

  @Override
  public final LObject deepClone() {
    return this;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result
            + ((getJavaValue() == null) ? 0 : getJavaValue().hashCode());
    return result;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DataWrapper other = (DataWrapper) obj;
    if (getJavaValue() == null) {
      if (other.getJavaValue() != null) {
        return false;
      }
    } else if (!getJavaValue().equals(other.getJavaValue())) {
      return false;
    }
    return true;
  }

}
