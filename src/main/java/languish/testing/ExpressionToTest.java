package languish.testing;

import languish.lambda.LObject;
import languish.lambda.Tuple;

public interface ExpressionToTest {

  public String name();

  public Tuple getExpression();

  public String getCode();

  public Tuple getReducedOnce();

  public LObject getReducedCompletely();
}
