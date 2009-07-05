package languish.prim.data;

import static languish.lambda.Lambda.*;
import static languish.prim.data.LBoolean.*;
import static languish.prim.data.LBooleans.*;
import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Tuple;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class LBooleanTest extends TestCase {
  public enum Tests implements ExpressionToTest {
    // IDENITY TESTS
    GET_TRUE(data(TRUE), //
        "TRUE",
        null,
        null),

    GET_FALSE(data(FALSE), //
        "FALSE",
        null,
        null),

    // BASIC TESTS
    LITERAL_TRUE(prim(BRANCH, data(TRUE)), //
        "[PRIM BRANCH TRUE]",
        BRANCH_THEN,
        null),

    LITERAL_FALSE(prim(BRANCH, data(FALSE)), //
        "[PRIM BRANCH FALSE]",
        BRANCH_ELSE,
        null),

    APPLICATION_ON_SELECTOR(prim(BRANCH, app(IDENT, data(TRUE))),
        "[PRIM BRANCH [APP [ABS [REF 1]] TRUE]]",
        LITERAL_TRUE.expression,
        null),

    LOOP_ON_SELECTOR(prim(BRANCH, LOOP),
        "[PRIM BRANCH [APP [ABS [APP [REF 1] [REF 1]]] "
            + "[ABS [APP [REF 1] [REF 1]]]]]",
        prim(BRANCH, LOOP),
        null),

    // BRANCH TESTS
    BASIC_BRANCH_THEN(app(app(BRANCH_THEN, data(FIVE)), data(FOUR)), //
        "[APP [APP [ABS [ABS [REF 2]]] [DATA 5]] [DATA 4]]",
        app(abs(data(LInteger.of(5))), data(LInteger.of(4))),
        FIVE),

    BASIC_BRANCH_ELSE(app(app(BRANCH_ELSE, data(FIVE)), data(FOUR)), //
        "[APP [APP [ABS [ABS [REF 1]]] [DATA 5]] [DATA 4]]",
        app(abs(ref(1)), data(LInteger.of(4))),
        FOUR),

    LOOP_ON_BRANCH(app(app(BRANCH_THEN, LOOP), data(FIVE)), //
        "[APP [APP [ABS [ABS [REF 2]]] [APP [ABS [APP [REF 1] [REF 1]]] "
            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
        app(abs(LOOP), data(LInteger.of(5))),
        null),

    LOOP_ON_BRANCH_2(LOOP_ON_BRANCH.reducedOnce, //
        "[APP [ABS [APP [ABS [APP [REF 1] [REF 1]]] "
            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
        LOOP,
        null),

    LOOP_OFF_BRANCH(app(app(BRANCH_ELSE, LOOP), data(FIVE)), //
        "[APP [APP [ABS [ABS [REF 1]]] [APP [ABS [APP [REF 1] [REF 1]]] "
            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
        app(abs(ref(1)), data(LInteger.of(5))),
        FIVE),

    // OVERALL
    WHOLE_SHEBANG(app(app(prim(BRANCH, data(TRUE)), app(IDENT, data(FOUR))),
        data(FIVE)), //
        "[APP [APP [PRIM BRANCH TRUE] "
            + "[APP [ABS [REF 1]] [DATA 4]]] [DATA 5]]",
        app(app(abs(abs(ref(2))), app(abs(ref(1)), data(LInteger.of(4)))),
            data(LInteger.of(5))),
        FOUR),

    NESTED(app(app(prim(BRANCH, //
        app(app(prim(BRANCH, data(TRUE)), data(FALSE)), data(TRUE))),
        data(FOUR)), data(FIVE)), //
        "[APP [APP [PRIM BRANCH [APP [APP [PRIM BRANCH TRUE] FALSE] TRUE]] "
            + "[DATA 4]] [DATA 5]]",
        app(app(prim(BRANCH,
            app(app(abs(abs(ref(2))), data(FALSE)), data(TRUE))), data(FOUR)),
            data(FIVE)),
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
