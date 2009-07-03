package languish.lambda;


public abstract class DataFunction extends ImmutableLObject {

  public abstract Tuple apply(LObject arg);

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    return System.identityHashCode(this);
  }
}
