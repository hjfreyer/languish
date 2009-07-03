package languish.lambda;

import static languish.testing.TestConstants.*;
import junit.framework.TestCase;

public class AbstractionTest extends TestCase {

  private static final Tuple IDENTITY = Abstraction.of(Reference.to(1));

  private static final Tuple FIRST_PICKER =
      Abstraction.of(Abstraction.of(Reference.to(2)));
  private static final Tuple SECOND_PICKER =
      Abstraction.of(Abstraction.of(Reference.to(1)));

  private static final Tuple OMEGA =
      Abstraction.of(Application.of(Reference.to(1), Reference.to(1)));
  private static final Tuple LOOP = Application.of(OMEGA, OMEGA);

  public void testBlob() {
    assertEquals(THREE, Lambda.reduce(w(THREE)));
  }

  public void testBasicApply() {
    Tuple applyFour = Application.of(IDENTITY, w(FOUR));

    assertEquals(w(FOUR), Lambda.reduceTupleOnce(applyFour));
    assertEquals(FOUR, Lambda.reduce(applyFour));
  }

  public void testArgumentChooser() {
    assertEquals(FOUR, Lambda.reduce(Application.of(Application.of(
        FIRST_PICKER, w(FOUR)), w(FIVE))));

    assertEquals(FIVE, Lambda.reduce(Application.of(Application.of(
        SECOND_PICKER, w(FOUR)), w(FIVE))));
  }

  public void testIrrelevantNonHalter() {
    assertEquals(FOUR, Lambda.reduce(Application.of(Application.of(
        FIRST_PICKER, w(FOUR)), LOOP)));
  }

  public void testRelevantNonHalterFunction() {
    Tuple exp =
        Lambda.reduceTupleOnce(Application.of(Application.of(SECOND_PICKER,
            w(FOUR)), LOOP));

    assertEquals(LOOP, Lambda.reduceTupleOnce(exp));
  }

  public void testNonHalter() {
    assertEquals(LOOP, Lambda.reduceTupleOnce(LOOP));
  }
}
