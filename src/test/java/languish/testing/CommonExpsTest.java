package languish.testing;

import static languish.testing.CommonExps.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Tuple;

public class CommonExpsTest extends TestCase {
  public enum Tests implements ExpressionToTest {

    OMEGA_(OMEGA, OMEGA_CODE),
    LOOP_(LOOP, LOOP_CODE),

    // LISTS
    EMPTY_LIST_(EMPTY_LIST, EMPTY_LIST_CODE),
    CAR_(CAR, CAR_CODE),
    CDR_(CDR, CDR_CODE),
    PUSHL_(PUSHL, PUSHL_CODE),
    ADDL_(ADDL, ADDL_CODE),

    ;

    private final Tuple expression;
    private final String code;

    private Tests(Tuple expression, String code) {
      this.expression = expression;
      this.code = code;
    }

    public Tuple getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Tuple getReducedOnce() {
      return null;
    }

    public LObject getReducedCompletely() {
      return null;
    }
  }

  public void test() {
    ExpressionTester.testExpressions(Arrays.asList(Tests.values()));
  }
}
