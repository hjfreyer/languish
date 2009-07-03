package languish.prim.data;

import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Data;
import languish.lambda.Reference;
import languish.lambda.Tuple;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class LBooleanTest extends TestCase {
  public enum Tests implements ExpressionToTest {
    // IDENITY TESTS
    TRUE(w(LBoolean.TRUE), //
        "(~TRUE~)",
        null,
        null),

    FALSE(w(LBoolean.FALSE), //
        "(~FALSE~)",
        null,
        null),

    // BASIC TESTS
    LITERAL_TRUE(Application.of(LBooleans.BRANCH, w(LBoolean.TRUE)), //
        "(APP (~BRANCH~) (~TRUE~))",
        LBooleans.BRANCH_THEN,
        null),

    LITERAL_FALSE(Application.of(LBooleans.BRANCH, w(LBoolean.FALSE)), //
        "(APP (~BRANCH~) (~FALSE~))",
        LBooleans.BRANCH_ELSE,
        null),

    APPLICATION_ON_SELECTOR(Application.of(LBooleans.BRANCH, Application.of(
        IDENT, w(LBoolean.TRUE))),
        "(APP (~BRANCH~) (APP (ABS +1) (~TRUE~)))",
        LITERAL_TRUE.expression,
        null),

    LOOP_ON_SELECTOR(Application.of(LBooleans.BRANCH, LOOP),
        "(APP (~BRANCH~) (APP (ABS (APP +1 +1)) (ABS (APP +1 +1))))",
        Application.of(LBooleans.BRANCH, LOOP),
        null),

    // BRANCH TESTS
    BASIC_BRANCH_THEN(Application.of(Application.of(LBooleans.BRANCH_THEN,
        w(FIVE)), w(FOUR)), //
        "(APP (APP (ABS (ABS +2)) (!5!)) (!4!))",
        Application.of(Abstraction.of(Data.of(LInteger.of(5))), Data
            .of(LInteger.of(4))),
        FIVE),

    BASIC_BRANCH_ELSE(Application.of(Application.of(LBooleans.BRANCH_ELSE,
        w(FIVE)), w(FOUR)), //
        "(APP (APP (ABS (ABS +1)) (!5!)) (!4!))",
        Application
            .of(Abstraction.of(Reference.to(1)), Data.of(LInteger.of(4))),
        FOUR),

    LOOP_ON_BRANCH(Application.of(Application.of(LBooleans.BRANCH_THEN, LOOP),
        w(FIVE)), //
        "(APP (APP (ABS (ABS +2)) (APP (ABS (APP +1 +1)) "
            + "(ABS (APP +1 +1)))) (!5!))",
        Application.of(Abstraction.of(LOOP), Data.of(LInteger.of(5))),
        null),

    LOOP_ON_BRANCH_2(LOOP_ON_BRANCH.reducedOnce, //
        "(APP (ABS (APP (ABS (APP +1 +1)) (ABS (APP +1 +1)))) (!5!))",
        LOOP,
        null),

    LOOP_OFF_BRANCH(Application.of(Application.of(LBooleans.BRANCH_ELSE, LOOP),
        w(FIVE)), //
        "(APP (APP (ABS (ABS +1)) (APP (ABS (APP +1 +1)) "
            + "(ABS (APP +1 +1)))) (!5!))",
        Application
            .of(Abstraction.of(Reference.to(1)), Data.of(LInteger.of(5))),
        FIVE),

    // OVERALL
    WHOLE_SHEBANG(Application.of(Application.of(Application.of(
        LBooleans.BRANCH, w(LBoolean.TRUE)), Application.of(IDENT, w(FOUR))),
        w(FIVE)), //
        "(APP (APP (APP (~BRANCH~) (~TRUE~)) (APP (ABS +1) (!4!))) (!5!))",
        Application.of(Application.of(Abstraction.of(Abstraction.of(Reference
            .to(2))), Application.of(Abstraction.of(Reference.to(1)), Data
            .of(LInteger.of(4)))), Data.of(LInteger.of(5))),
        FOUR),

    NESTED(Application.of(
        Application.of(Application.of(LBooleans.BRANCH, //
            Application.of(Application.of(Application.of(LBooleans.BRANCH,
                w(LBoolean.TRUE)), w(LBoolean.FALSE)), w(LBoolean.TRUE))),
            w(FOUR)), w(FIVE)), //
        "(APP (APP (APP (~BRANCH~) "
            + "(APP (APP (APP (~BRANCH~) (~TRUE~)) (~FALSE~)) (~TRUE~))) "
            + "(!4!)) (!5!))",
        Application.of(Application.of(Application.of(LBooleans.BRANCH,
            Application.of(Application.of(Abstraction.of(Abstraction
                .of(Reference.to(2))), Data.of(LBoolean.FALSE)), Data
                .of(LBoolean.TRUE))), Data.of(LInteger.of(4))), Data
            .of(LInteger.of(5))),
        FIVE),

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
