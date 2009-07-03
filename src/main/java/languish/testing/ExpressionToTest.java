package languish.testing;

import languish.lambda.Tuple;
import languish.prim.data.LObject;

public interface ExpressionToTest {

  public String name();

  public Tuple getExpression();

  public String getCode();

  public Tuple getReducedOnce();

  public LObject getReducedCompletely();
}
