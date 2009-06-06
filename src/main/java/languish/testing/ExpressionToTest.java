package languish.testing;

import languish.lambda.Expression;
import languish.prim.data.LObject;

public interface ExpressionToTest {

  public String name();

  public Expression getExpression();

  public String getCode();

  public Expression getReducedOnce();

  public LObject getReducedCompletely();
}
