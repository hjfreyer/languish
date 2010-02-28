package languish.lang;

import static languish.base.Terms.*;
import static languish.base.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.base.Terms;
import languish.base.testing.TestUtil;
import languish.lib.Integers;
import languish.util.PrimitiveTree;

public class IntegersTest extends TestCase {
	public void testAdd() {
		TestUtil.assertReducesToData(PrimitiveTree.from(7), app(app(
				Integers.add(),
				Terms.primitive(FIVE)), Terms.primitive(TWO)));
	}

	public void testMultiply() {
		TestUtil.assertReducesToData(PrimitiveTree.from(10), app(app(Integers
				.multiply(), Terms.primitive(TestUtil.FIVE)), Terms.primitive(TWO)));
	}
}
