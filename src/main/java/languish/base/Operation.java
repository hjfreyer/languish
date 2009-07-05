package languish.base;

public abstract class Operation extends LObject {

  public abstract Tuple reduceOnce(Tuple tuple);

  @Override
  public final LObject deepClone() {
    return this;
  }

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    return System.identityHashCode(this);
  }
}
