package languish.lambda;

import static languish.base.LObject.EQUALS;
import static languish.base.Lambda.*;
import static languish.testing.TestUtil.TWO;
import junit.framework.TestCase;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.prim.data.LBoolean;
import languish.prim.data.LInteger;

public class EqualityTest extends TestCase {

  public void testIntegerEquals() {
    assertReducesTrue(prim(EQUALS, data(Tuple.of(TWO, TWO))));
    assertReducesTrue(prim(EQUALS, data(Tuple.of(LInteger.of(2), TWO))));
    assertReducesFalse(prim(EQUALS, data(Tuple.of(LInteger.of(3), TWO))));
    assertReducesFalse(prim(EQUALS, data(Tuple
        .of(Tuple.of(LInteger.of(3)), TWO))));
  }

  public void testIntegerPair() {
    assertReducesTrue(prim(EQUALS, cons(data(TWO), data(TWO))));
    assertReducesTrue(prim(EQUALS, cons(data(LInteger.of(2)), data(TWO))));
    assertReducesFalse(prim(EQUALS, cons(data(LInteger.of(3)), data(TWO))));
    assertReducesFalse(prim(EQUALS, cons(data(Tuple.of(LInteger.of(3))),
        data(TWO))));
  }

  public void assertReducesTrue(Tuple exp) {
    assertEquals(LBoolean.TRUE, Lambda.reduce(exp));
  }

  public void assertReducesFalse(Tuple exp) {
    assertEquals(LBoolean.FALSE, Lambda.reduce(exp));
  }
}
