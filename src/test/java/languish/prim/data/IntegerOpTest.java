package languish.prim.data;

import static languish.prim.data.LIntegers.ADD;
import static languish.testing.TestConstants.*;
import junit.framework.TestCase;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Lambda;
import languish.lambda.Reference;
import languish.lambda.Tuple;

public class IntegerOpTest extends TestCase {

  private static final Tuple DOUBLE_FUNC =
      Abstraction.of(Application.of(Application.of(LIntegers.ADD, Reference
          .to(1)), Reference.to(1)));

  public void testBasicAdd() {
    Tuple addition1 = Application.of(Application.of(ADD, w(FOUR)), w(FIVE));

    Tuple addition2 = Application.of(Application.of(ADD, w(FIVE)), w(FOUR));

    assertEquals(NINE, Lambda.reduce(addition1));
    assertEquals(NINE, Lambda.reduce(addition2));
  }

  public void testTripleAdd() {
    Tuple addition1 = Application.of(Application.of(ADD, w(FOUR)), w(FIVE));

    Tuple tripleAdd = Application.of(Application.of(ADD, addition1), w(THREE));

    assertEquals(TWELVE, Lambda.reduce(tripleAdd));
  }

  public void testDoubleFunc() {
    assertEquals(EIGHT, Lambda.reduce(Application.of(DOUBLE_FUNC, w(FOUR))));

  }
}
