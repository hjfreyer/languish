package languish.prim.functions;

import static languish.testing.TestConstants.*;
import junit.framework.TestCase;
import languish.lambda.Application;
import languish.prim.data.LComposite;
import languish.prim.data.LObject;

public class CompositeTest extends TestCase {

  private static final LComposite PAIR =
      LComposite.of(new LObject[] { FOUR, FIVE });

  private static final LComposite TWO_DEEP =
      LComposite.of(new LObject[] { PAIR, FIVE });

  public void testGetter() {
    assertEquals(FOUR, Application.of(
        Application.of(CompositeOps.GET_ELEMENT, w(PAIR)), w(ZERO))
        .reduceCompletely());

    assertEquals(FIVE, Application.of(
        Application.of(CompositeOps.GET_ELEMENT, w(PAIR)), w(ONE))
        .reduceCompletely());
  }

  public void test() {}
}
