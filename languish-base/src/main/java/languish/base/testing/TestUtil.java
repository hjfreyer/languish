package languish.base.testing;

import junit.framework.Assert;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

import com.hjfreyer.util.Tree;

public class TestUtil {

	public static final Primitive ZERO = new Primitive(0);
	public static final Primitive ONE = new Primitive(1);
	public static final Primitive TWO = new Primitive(2);
	public static final Primitive THREE = new Primitive(3);
	public static final Primitive FOUR = new Primitive(4);
	public static final Primitive FIVE = new Primitive(5);
	public static final Primitive SIX = new Primitive(6);
	public static final Primitive SEVEN = new Primitive(7);
	public static final Primitive EIGHT = new Primitive(8);
	public static final Primitive NINE = new Primitive(9);
	public static final Primitive TEN = new Primitive(10);
	public static final Primitive ELEVEN = new Primitive(11);
	public static final Primitive TWELVE = new Primitive(12);
	public static final Primitive THIRTEEN = new Primitive(13);
	public static final Primitive FOURTEEN = new Primitive(14);
	public static final Primitive FIFTEEN = new Primitive(15);

	public static final Term IDENT = Terms.abs(Terms.ref(1));
	public static final Term OMEGA =
			Terms.abs(Terms.app(Terms.ref(1), Terms.ref(1)));
	public static final Term LOOP = Terms.app(TestUtil.OMEGA, TestUtil.OMEGA);

	public static void assertLanguishTestCase(LanguishTestCase testCase) {

		String name = testCase.name();
		Term exp = testCase.getExpression();
		Term reducedOnce = testCase.getReducedOnce();
		Tree<Primitive> reducedCompletely = testCase.getReducedCompletely();

		try {
			// REDUCE COMPLETELY
			if (reducedCompletely != null) {
				Assert.assertEquals(
						"on test "
								+ name
								+ " - expression does not ultimately reduce to given value:",
						reducedCompletely,
						Terms.convertTermToJavaObject(exp, 0));
			}

			// REDUCE ONCE
			if (reducedOnce != null) {
				Assert.assertEquals(
						"on test "
								+ name
								+ " - expression does not reduce once to given value:",
						reducedOnce,
						exp.reduce());
			}
		} catch (Exception e) {
			throw new RuntimeException("Test " + name + " failed.", e);
		}
	}

	public static void assertReducesToData(Tree<Primitive> expected, Term actual) {
		Assert.assertEquals(expected, Terms.convertTermToJavaObject(actual, 0));
	}
}
