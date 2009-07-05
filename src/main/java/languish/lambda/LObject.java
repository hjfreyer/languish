package languish.lambda;

import languish.prim.data.LBoolean;

public abstract class LObject {

  public static final DataFunction EQUALS = new DataFunction() {
    @Override
    public Tuple apply(LObject arg) {
      Tuple pair = (Tuple) arg;
      return Lambda.data(LBoolean.of(pair.getFirst().equals(pair.getSecond())));
    }
  };

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