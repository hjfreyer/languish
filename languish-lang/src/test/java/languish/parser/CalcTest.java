package languish.parser;

import junit.framework.TestCase;
import languish.base.Term;
import languish.lib.testing.LibTestUtil;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

public class CalcTest extends TestCase {

  public void test() {
    Term lib = LibTestUtil.loadLib("parser/calc_test");
    TestUtil.assertReducesToData(PrimitiveTree.from(5), lib);
  }
}
