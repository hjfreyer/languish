package languish.prim.data;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.Expression;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class LMapTest extends TestCase {

  public enum Tests implements ExpressionToTest {

//    FUNCTION_EQUALITY(LMaps.EMPTY_MAP, //
//        "(~EMPTY_MAP~)",
//        null,
//        null),
//
//    FUNCTION_EQUALITY_2(LComposites.WRAP, //
//        "(~WRAP~)",
//        null,
//        null),

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
    ExpressionTester.testExpressions(Arrays.asList(Tests.values()));
  }
}
