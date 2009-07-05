package languish.prim.data;

import static languish.lambda.Lambda.*;
import static languish.prim.data.LIntegers.ADD;
import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class LIntegerTest extends TestCase {

  public static final Tuple DOUBLE_FUNC = abs(prim(ADD, pair(ref(1), ref(1))));

  public enum Tests implements ExpressionToTest {
    // IDENITY TESTS
    TEST_DATA(data(FIVE), //
        "[DATA 5]",
        null,
        FIVE),

    TEST_SIMPLE_ADD(prim(ADD, pair(data(FIVE), data(THREE))),
        "[PRIM ADD [PAIR [DATA 5] [DATA 3]]]",
        null,
        EIGHT),

    TEST_DOUBLE(Lambda.app(DOUBLE_FUNC, data(FOUR)), //
        "[APP [ABS [PRIM ADD [PAIR [REF 1] [REF 1]]]] [DATA 4]]",
        prim(ADD, pair(data(FOUR), data(FOUR))),
        EIGHT),

    ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final LObject reducedCompletely;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Tuple getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Tuple getReducedOnce() {
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
