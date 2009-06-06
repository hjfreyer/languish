package languish.lambda;

import static languish.testing.TestConstants.*;
import junit.framework.TestCase;

public class AbstractionTest extends TestCase {

  private static final Abstraction IDENTITY = Abstraction.of(Reference.to(1));

  private static final Expression FIRST_PICKER =
      Abstraction.of(Abstraction.of(Reference.to(2)));
  private static final Expression SECOND_PICKER =
      Abstraction.of(Abstraction.of(Reference.to(1)));

  private static final Expression OMEGA =
      Abstraction.of(Application.of(Reference.to(1), Reference.to(1)));
  private static final Expression LOOP = Application.of(OMEGA, OMEGA);

  public void testBlob() {
    assertEquals(THREE, w(THREE).reduceCompletely());
  }

  public void testBasicApply() {
    Expression applyFour = Application.of(IDENTITY, w(FOUR));

    assertEquals(w(FOUR), applyFour.reduceOnce());
    assertEquals(FOUR, applyFour.reduceCompletely());
  }

  public void testArgumentChooser() {
    assertEquals(FOUR, Application.of(Application.of(FIRST_PICKER, w(FOUR)),
        w(FIVE)).reduceCompletely());

    assertEquals(FIVE, Application.of(Application.of(SECOND_PICKER, w(FOUR)),
        w(FIVE)).reduceCompletely());
  }

  public void testIrrelevantNonHalter() {
    assertEquals(FOUR, Application.of(Application.of(FIRST_PICKER, w(FOUR)),
        LOOP).reduceCompletely());
  }

  public void testRelevantNonHalterFunction() {
    Expression exp =
        Application.of(Application.of(SECOND_PICKER, w(FOUR)), LOOP)
            .reduceOnce();

    assertEquals(LOOP, exp.reduceOnce());
  }

  public void testNonHalter() {
    assertEquals(LOOP, LOOP.reduceOnce());
  }
}
