package languish.lambda;


public abstract class Operation extends ImmutableLObject {

  public abstract Tuple reduceOnce(Tuple tuple);

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    return System.identityHashCode(this);
  }
}
