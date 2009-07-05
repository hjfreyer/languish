package languish.testing;

import languish.base.LObject;
import languish.base.Tuple;

public interface ExpressionToTest {

  public String name();

  public Tuple getExpression();

  public String getCode();

  public Tuple getReducedOnce();

  public LObject getReducedCompletely();
}
