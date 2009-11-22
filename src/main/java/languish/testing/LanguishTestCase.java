package languish.testing;

import languish.lambda.Term;
import languish.util.PrimitiveTree;

public interface LanguishTestCase {

  public String name();

  public Term getExpression();

  public String getCode();

  public Term getReducedOnce();

  public PrimitiveTree getReducedCompletely();
}
