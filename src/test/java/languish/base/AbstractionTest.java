package languish.base;

import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.testing.TestUtil;

public class AbstractionTest extends TestCase {

  private static final Tuple IDENTITY = Lambda.abs(Lambda.ref(1));

  private static final Tuple FIRST_PICKER =
      Lambda.abs(Lambda.abs(Lambda.ref(2)));
  private static final Tuple SECOND_PICKER =
      Lambda.abs(Lambda.abs(Lambda.ref(1)));

  private static final Tuple OMEGA =
      Lambda.abs(Lambda.app(Lambda.ref(1), Lambda.ref(1)));
  private static final Tuple LOOP = Lambda.app(OMEGA, OMEGA);

  public void testBlob() {
    assertEquals(THREE, Lambda.reduce(Lambda.data(THREE)));
  }

  public void testBasicApply() {
    Tuple applyFour = Lambda.app(IDENTITY, Lambda.data(FOUR));

    assertEquals(Lambda.data(FOUR), TestUtil.reduceTupleOnce(applyFour));
    assertEquals(FOUR, Lambda.reduce(applyFour));
  }

  public void testArgumentChooser() {
    assertEquals(FOUR, Lambda.reduce(Lambda.app(Lambda.app(FIRST_PICKER, Lambda
        .data(FOUR)), Lambda.data(FIVE))));

    assertEquals(FIVE, Lambda.reduce(Lambda.app(Lambda.app(SECOND_PICKER,
        Lambda.data(FOUR)), Lambda.data(FIVE))));
  }

  public void testIrrelevantNonHalter() {
    assertEquals(FOUR, Lambda.reduce(Lambda.app(Lambda.app(FIRST_PICKER, Lambda
        .data(FOUR)), LOOP)));
  }

  public void testRelevantNonHalterFunction() {
    Tuple exp =
        TestUtil.reduceTupleOnce(Lambda.app(Lambda.app(SECOND_PICKER, Lambda
            .data(FOUR)), LOOP));

    assertEquals(LOOP, TestUtil.reduceTupleOnce(exp));
  }

  public void testNonHalter() {
    assertEquals(LOOP, TestUtil.reduceTupleOnce(LOOP));
  }
}