package languish.lambda;


public abstract class LObject {

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