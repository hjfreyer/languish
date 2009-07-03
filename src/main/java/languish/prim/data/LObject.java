package languish.prim.data;

import languish.lambda.Canonizer;

public abstract class LObject {

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

  @Override
  public String toString() {
    return Canonizer.getCodeForExpression(this);
  }
}