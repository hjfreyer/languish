package languish.base;

import languish.testing.Canonizer;

public abstract class LObject {

  // public static final DataFunction EQUALS = new DataFunction() {
  // @Override
  // public Tuple apply(LObject... args) {
  // LObject a = args[0];
  // LObject b = args[1];
  //
  // return Lambda.data(LBoolean.of(a.equals(b)));
  // }
  // };

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

  public abstract LObject deepClone();

  @Override
  public String toString() {
    return Canonizer.getCodeForExpression(this);
  }

}