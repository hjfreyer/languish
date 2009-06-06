package languish.prim.data;

import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.prim.data.LComposites;
import languish.prim.data.LComposite;
import languish.prim.data.LObject;
import languish.testing.ExpressionToTest;
import languish.testing.TestExpressions;

public class CompositeTest extends TestCase {

  private static final LComposite PAIR =
      LComposite.of(new LObject[] { FOUR, FIVE });

  private static final LComposite TWO_DEEP =
      LComposite.of(new LObject[] { PAIR, SIX });

  public enum Tests implements ExpressionToTest {

    SIMPLE_GET(Application.of(
        Application.of(LComposites.GET_ELEMENT, w(PAIR)), w(ZERO)), //
        null,
        null,
        FOUR),

    SIMPLE_GET_OTHER(Application.of(Application.of(LComposites.GET_ELEMENT,
        w(PAIR)), w(ONE)), //
        null,
        null,
        FIVE),

    TWO_DEEP_GET(Application.of(Application.of(LComposites.GET_ELEMENT,
        w(TWO_DEEP)), w(ZERO)), //
        null,
        null,
        PAIR),

    TWO_DEEP_GET_TWICE(Application.of(Application.of(LComposites.GET_ELEMENT,
        Application.of(Application.of(LComposites.GET_ELEMENT, w(TWO_DEEP)),
            w(ZERO))), w(ONE)), //
        null,
        null,
        FIVE),

    WRAP_TEST_SIMPLE(Application.of(Application.of(Application.of(
        LComposites.WRAP, w(TWO)), w(FOUR)), w(FIVE)),
        "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))",
        null,
        PAIR)

    ;

    private final Expression expression;
    private final String code;
    private final Expression reducedOnce;
    private final LObject reducedCompletely;

    private Tests(Expression expression, String code, Expression reducedOnce,
        LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Expression getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Expression getReducedOnce() {
      return reducedOnce;
    }

    public LObject getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    TestExpressions.testExpressions(Arrays.asList(Tests.values()));
  }
}
