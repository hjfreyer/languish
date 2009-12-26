package languish.tools.testing;

import languish.base.Primitive;
import languish.base.Term;

import com.hjfreyer.util.Tree;

public interface LanguishTestCase {

  public String name();

  public Term getExpression();

  public String getCode();

  public Term getReducedOnce();

  public Tree<Primitive> getReducedCompletely();
}
