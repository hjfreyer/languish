package languish.testing;

import languish.base.Term;
import languish.util.PrimitiveTree;

public interface LanguishTestCase {

  public String name();

  public Term getExpression();

  public String getCode();

  public Term getReducedOnce();

  public PrimitiveTree getReducedCompletely();
}
