package languish.base;

public abstract class PrimitiveFunction extends LObject {

  public abstract Tuple convertLeaf(LObject leaf);

  public abstract Tuple combineChildren(LObject arg1, LObject arg2);

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

  //
  // public static abstract class TwoValuePrimitiveFunction extends
  // PrimitiveFunction {
  // @Override
  // public final Tuple apply(Tuple arg) {
  // if (arg.getFirst() != Lambda.CONS) {
  // throw new IllegalArgumentException("Expected two values: " + arg);
  // }
  //
  // return apply((Tuple) arg.getSecond(), (Tuple) arg.getThird());
  // }
  //
  // public abstract Tuple apply(Tuple arg1, Tuple arg2);
  // }

  public static abstract class TwoValueDataFunction extends PrimitiveFunction {
    @Override
    public final Tuple convertLeaf(LObject arg0) {
      throw new IllegalArgumentException(
          "function cannot be called with 1 argument");
    }

    @Override
    public Tuple combineChildren(LObject arg1, LObject arg2) {
      return apply(arg1, arg2);
    }

    public abstract Tuple apply(LObject arg1, LObject arg2);
  }

  public static abstract class SingleValueDataFunction extends
      PrimitiveFunction {
    @Override
    public final Tuple convertLeaf(LObject arg) {
      return apply(arg);
    }

    @Override
    public final Tuple combineChildren(LObject arg1, LObject arg2) {
      throw new IllegalArgumentException(
          "function cannot be called with multiple arguments");
    }

    public abstract Tuple apply(LObject arg1);
  }

  //
  // public abstract Tuple applyToData(LObject arg1, LObject arg2);
  // }
  //
  // public static abstract class DataFunction extends PrimitiveFunction {
  // @Override
  // public final Tuple apply(Tuple arg) {
  // if (arg.getFirst() != Lambda.DATA) {
  // throw new IllegalArgumentException("Expected DATA: " + arg);
  // }
  //
  // return applyToData(arg.getSecond());
  // }
  //
  // public abstract Tuple applyToData(LObject data);
  // }
  //
  // public static abstract class RecursivePrimitiveFunction extends
  // PrimitiveFunction {
  // @Override
  // public final Tuple apply(Tuple arg) {
  // if (arg.getFirst() == CONS) {
  // Tuple car = (Tuple) arg.getSecond();
  // Tuple cdr = (Tuple) arg.getThird();
  //
  // if (car.getFirst() != DATA) {
  // return prim(this, cons(prim(this, car), cdr));
  // }
  // if (cdr.getFirst() != DATA) {
  // Tuple prim = prim(this, cons(car, prim(this, cdr)));
  // return prim;
  // }
  // return combineChildren(car.getSecond(), cdr.getSecond());
  // }
  //
  // return convertLeaf(arg);
  // }
  //
  // @Override
  // public abstract Tuple convertLeaf(Tuple leaf);
  //
  // @Override
  // public abstract Tuple combineChildren(LObject arg1, LObject arg2);
  // }
  //
  public static abstract class NullLeafPrimitiveFunction extends
      PrimitiveFunction {

    public abstract LObject getNullValue();

    public abstract Tuple reduce(LObject arg1, LObject arg2);

    @Override
    public Tuple convertLeaf(LObject arg) {
      if (!arg.equals(Tuple.of())) {
        throw new IllegalArgumentException("Leaves must be NULL");
      }

      return Lambda.data(getNullValue());
    }

    @Override
    public final Tuple combineChildren(LObject arg0, LObject arg1) {
      arg0 = arg0.equals(Tuple.of()) ? getNullValue() : arg0;
      arg1 = arg1.equals(Tuple.of()) ? getNullValue() : arg1;

      return reduce(arg0, arg1);
    }

  }
}
