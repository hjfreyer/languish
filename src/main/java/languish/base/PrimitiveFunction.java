package languish.base;

public abstract class PrimitiveFunction extends LObject {

  // TODO: stupid hack to copy defensively the result of any prim function.
  public final Tuple applyWithCopy(Tuple arg) {
    return apply(arg).deepClone();
  }

  protected abstract Tuple apply(Tuple arg);

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

  public static abstract class TwoArgDataFunction extends PrimitiveFunction {
    @Override
    public final Tuple apply(Tuple arg) {
      if (arg.getFirst() != Lambda.CONS) {
        throw new IllegalArgumentException(
            "function must be called with a single CONS: " + arg);
      }

      Tuple arg1 = (Tuple) arg.getSecond();
      Tuple arg2 = (Tuple) arg.getThird();

      if (arg1.getFirst() != Lambda.DATA) {
        throw new IllegalArgumentException(
            "first element of cons must be DATA: " + arg1);
      }
      if (arg2.getFirst() != Lambda.DATA) {
        throw new IllegalArgumentException(
            "second element of cons must be DATA: " + arg2);
      }

      return apply(arg1.getSecond(), arg2.getSecond());
    }

    public abstract Tuple apply(LObject arg1, LObject arg2);
  }

  public static abstract class SingleArgDataFunction extends PrimitiveFunction {
    @Override
    public final Tuple apply(Tuple arg) {
      if (arg.getFirst() != Lambda.DATA) {
        throw new IllegalArgumentException("argument must be DATA: " + arg);
      }

      return apply(arg.getSecond());
    }

    public abstract Tuple apply(LObject arg);
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
  // public static abstract class NullLeafPrimitiveFunction extends
  // PrimitiveFunction {
  //
  // public abstract LObject getNullValue();
  //
  // public abstract Tuple reduce(LObject arg1, LObject arg2);
  //
  // @Override
  // public Tuple convertLeaf(LObject arg) {
  // if (!arg.equals(Tuple.of())) {
  // throw new IllegalArgumentException("Leaves must be NULL");
  // }
  //
  // return Lambda.data(getNullValue());
  // }
  //
  // @Override
  // public final Tuple combineChildren(LObject arg0, LObject arg1) {
  // arg0 = arg0.equals(Tuple.of()) ? getNullValue() : arg0;
  // arg1 = arg1.equals(Tuple.of()) ? getNullValue() : arg1;
  //
  // return reduce(arg0, arg1);
  // }
  //
  // }

}
