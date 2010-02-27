package languish.base.testing;

import languish.base.Primitive;
import languish.base.Term;

import com.hjfreyer.util.Tree;

public interface LanguishTestCase {

	public String name();

	public Term getExpression();

	public Term getReducedOnce();

	public Tree<Primitive> getReducedCompletely();
}
