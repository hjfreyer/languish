package languish.base;

public abstract class DataFunction extends LObject {

  public abstract Tuple applySingle(LObject arg);

  public abstract Tuple applyPair(LObject arg1, LObject arg2);

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

  public static abstract class TreeDataFunction extends DataFunction {
    public abstract LObject getNull();

    public abstract LObject reduce(LObject arg1, LObject arg2);

    @Override
    public final Tuple applySingle(LObject arg) {
      if (!arg.equals(Tuple.of())) {
        throw new IllegalArgumentException("Argument must be a tree");
      }
      return Lambda.data(getNull());
    }

    @Override
    public Tuple applyPair(LObject arg1, LObject arg2) {
      if (arg1.equals(Tuple.of())) {
        arg1 = getNull();
      }
      if (arg2.equals(Tuple.of())) {
        arg2 = getNull();
      }

      return Lambda.data(reduce(arg1, arg2));
    }
  }

  public static abstract class SingleArgDataFunction extends DataFunction {
    @Override
    public final Tuple applyPair(LObject arg1, LObject arg2) {
      throw new IllegalArgumentException("Function only accepts one argument.");
    }
  }

  public static abstract class TwoArgDataFunction extends DataFunction {
    @Override
    public final Tuple applySingle(LObject arg) {
      throw new IllegalArgumentException("Function only accepts two arguments");
    }
  }
}
