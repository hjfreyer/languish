package languish.interpreter.testing;

import junit.framework.Assert;
import languish.base.Primitive;
import languish.base.Term;
import languish.interpreter.Interpreter;

import com.hjfreyer.util.Tree;

public class InterpreterTestUtil {

	public static void assertReducesToData(Tree<Primitive> expected, Term actual) {
		Assert.assertEquals(expected, Interpreter.convertTermToJavaObject(actual));
	}
}
