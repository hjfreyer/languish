package languish.lambda;

import languish.prim.data.LBoolean;

public abstract class LObject {

  public static final DataFunction EQUALS = new DataFunction() {
    @Override
    public Tuple apply(final LObject obj1) {
      return Lambda.prim(new DataFunction() {
        @Override
        public Tuple apply(LObject obj2) {
          return Lambda.data(LBoolean.of(obj1.equals(obj2)));
        }
      });
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