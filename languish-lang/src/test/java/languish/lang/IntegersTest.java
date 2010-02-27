package languish.lang;

import static languish.base.Terms.app;
import junit.framework.TestCase;
import languish.base.Terms;
import languish.lib.Integers;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

public class IntegersTest extends TestCase {
	public void testAdd() {
		TestUtil.assertReducesToData(PrimitiveTree.from(7), app(app(
				Integers.add(),
				Terms.primitive(TestUtil.FIVE)), Terms.primitive(TestUtil.TWO)));
	}

	public void testMultiply() {
		TestUtil.assertReducesToData(PrimitiveTree.from(10), app(app(Integers
				.multiply(), Terms.primitive(TestUtil.FIVE)), Terms
				.primitive(TestUtil.TWO)));
	}
}
