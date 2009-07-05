package languish.lambda;

import static languish.lambda.Lambda.*;
import static languish.testing.TestConstants.TWO;
import junit.framework.TestCase;
import languish.prim.data.LBoolean;

public class EqualityTest extends TestCase {
  public void testIntegerEquals() {
    assertReducesTrue(prim(LObject.EQUALS, data(Tuple.of(TWO, TWO))));
  }

  public void assertReducesTrue(Tuple exp) {
    assertEquals(LBoolean.TRUE, Lambda.reduce(exp));
  }

  public void assertReducesFalse(Tuple exp) {
    assertEquals(LBoolean.FALSE, Lambda.reduce(exp));
  }
}
