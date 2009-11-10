package languish.base;

import static languish.testing.CommonExps.IDENTITY;
import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.lambda.Lambda;
import languish.lambda.Term;
import languish.testing.TestUtil;

public class AbstractionTest extends TestCase {

  private static final Term FIRST_PICKER =
      Lambda.abs(Lambda.abs(Lambda.ref(2)));
  private static final Term SECOND_PICKER =
      Lambda.abs(Lambda.abs(Lambda.ref(1)));

  private static final Term OMEGA =
      Lambda.abs(Lambda.app(Lambda.ref(1), Lambda.ref(1)));
  private static final Term LOOP = Lambda.app(OMEGA, OMEGA);

  public void testBlob() {
    assertEquals(THREE, Lambda.reduceToDataValue(Lambda.primitive(THREE)));
  }

  public void testBasicApply() {
    Term applyFour = Lambda.app(IDENTITY, Lambda.primitive(FOUR));

    assertEquals(Lambda.primitive(FOUR), TestUtil.reduceTupleOnce(applyFour));
    assertEquals(FOUR, Lambda.reduceToDataValue(applyFour));
  }

  public void testArgumentChooser() {
    assertEquals(FOUR, Lambda.reduceToDataValue(Lambda.app(Lambda.app(FIRST_PICKER, Lambda
        .primitive(FOUR)), Lambda.primitive(FIVE))));

    assertEquals(FIVE, Lambda.reduceToDataValue(Lambda.app(Lambda.app(SECOND_PICKER,
        Lambda.primitive(FOUR)), Lambda.primitive(FIVE))));
  }

  public void testIrrelevantNonHalter() {
    assertEquals(FOUR, Lambda.reduceToDataValue(Lambda.app(Lambda.app(FIRST_PICKER, Lambda
        .primitive(FOUR)), LOOP)));
  }

  public void testRelevantNonHalterFunction() {
    Term exp =
        TestUtil.reduceTupleOnce(Lambda.app(Lambda.app(SECOND_PICKER, Lambda
            .primitive(FOUR)), LOOP));

    assertEquals(LOOP, TestUtil.reduceTupleOnce(exp));
  }

  public void testNonHalter() {
    assertEquals(LOOP, TestUtil.reduceTupleOnce(LOOP));
  }
}
