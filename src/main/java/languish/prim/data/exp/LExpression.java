package languish.prim.data.exp;

import languish.lambda.Expression;
import languish.prim.data.LObject;

public abstract class LExpression extends LObject {

  public Expression toExpression() {
    throw new UnsupportedOperationException();
  }

}
