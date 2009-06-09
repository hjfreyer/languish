package languish.prim.functions;

import static languish.prim.data.LIntegers.ADD;
import static languish.testing.TestConstants.*;
import junit.framework.TestCase;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.Reducer;
import languish.lambda.Reference;
import languish.prim.data.LIntegers;

public class IntegerOpTest extends TestCase {

  private static final Expression DOUBLE_FUNC =
      Abstraction.of(Application.of(Application.of(LIntegers.ADD, Reference
          .to(1)), Reference.to(1)));

  public void testBasicAdd() {
    Application addition1 =
        Application.of(Application.of(ADD, w(FOUR)), w(FIVE));

    Application addition2 =
        Application.of(Application.of(ADD, w(FIVE)), w(FOUR));

    assertEquals(NINE, Reducer.reduce(addition1));
    assertEquals(NINE, Reducer.reduce(addition2));
  }

  public void testTripleAdd() {
    Application addition1 =
        Application.of(Application.of(ADD, w(FOUR)), w(FIVE));

    Application tripleAdd =
        Application.of(Application.of(ADD, addition1), w(THREE));

    assertEquals(TWELVE, Reducer.reduce(tripleAdd));
  }

  public void testDoubleFunc() {
    assertEquals(EIGHT, Reducer.reduce(Application.of(DOUBLE_FUNC, w(FOUR))));

  }
}
