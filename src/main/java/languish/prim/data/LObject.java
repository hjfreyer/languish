package languish.prim.data;

import languish.lambda.Expression;

public abstract class LObject {
  // public abstract Type getType();

  public abstract Expression getGeneratingExpression();

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

}