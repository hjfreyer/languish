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
    assertEquals(THREE, Reducer.reduce(w(THREE)));
  }

  public void testBasicApply() {
    Expression applyFour = Application.of(IDENTITY, w(FOUR));

    assertEquals(w(FOUR), Reducer.reduceOnce(applyFour));
    assertEquals(FOUR, Reducer.reduce(applyFour));
  }

  public void testArgumentChooser() {
    assertEquals(FOUR, Reducer.reduce(Application.of(Application.of(FIRST_PICKER, w(FOUR)),
    w(FIVE))));

    assertEquals(FIVE, Reducer.reduce(Application.of(Application.of(SECOND_PICKER, w(FOUR)),
    w(FIVE))));
  }

  public void testIrrelevantNonHalter() {
    assertEquals(FOUR, Reducer.reduce(Application.of(Application.of(FIRST_PICKER, w(FOUR)),
    LOOP)));
  }

  public void testRelevantNonHalterFunction() {
    Expression exp =
        Reducer.reduceOnce(Application.of(Application.of(SECOND_PICKER, w(FOUR)), LOOP));

    assertEquals(LOOP, Reducer.reduceOnce(exp));
  }

  public void testNonHalter() {
    assertEquals(LOOP, Reducer.reduceOnce(LOOP));
  }
}
