package languish.lambda;

import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.prim.data.LObject;
import languish.testing.ExpressionToTest;
import languish.testing.TestConstants;
import languish.testing.TestExpressions;

public class ExpressionTest extends TestCase {
  public enum Tests implements ExpressionToTest {
    LITERAL_INT(w(FIVE), //
        "(!5!)",
        w(FIVE),
        FIVE),

    IDENTITY(Application.of(IDENT, w(FOUR)), //
        "(APP (ABS +1) (!4!))",
        w(FOUR),
        FOUR),

    LOOP(TestConstants.LOOP, //
        "(APP (ABS (APP +1 +1)) (ABS (APP +1 +1)))",
        TestConstants.LOOP,
        null),

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
