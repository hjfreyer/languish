package languish.primitives;

import static languish.base.Lambda.*;
import static languish.primitives.DataFunctions.*;
import static languish.primitives.LBoolean.*;
import static languish.testing.CommonExps.LOOP;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class LBooleanTest extends TestCase {
  public enum Tests implements LanguishTestList {
    // IDENITY TESTS
    GET_TRUE(data(TRUE), //
        "[DATA TRUE]",
        null,
        TRUE),

    GET_FALSE(data(FALSE), //
        "[DATA FALSE]",
        null,
        FALSE),

    // BASIC TESTS
    LITERAL_TRUE(prim(BRANCH, data(TRUE)), //
        "[PRIM BRANCH [DATA TRUE]]",
        BRANCH_THEN,
        null),

    LITERAL_FALSE(prim(BRANCH, data(FALSE)), //
        "[PRIM BRANCH [DATA FALSE]]",
        BRANCH_ELSE,
        null),

    APPLICATION_ON_SELECTOR(prim(BRANCH, app(IDENT, data(TRUE))),
        "[PRIM BRANCH [APP [ABS [REF 1]] [DATA TRUE]]]",
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
        "[APP [APP [PRIM BRANCH [DATA TRUE]] "
            + "[APP [ABS [REF 1]] [DATA 4]]] [DATA 5]]",
        app(app(abs(abs(ref(2))), app(abs(ref(1)), data(LInteger.of(4)))),
            data(LInteger.of(5))),
        FOUR),

    NESTED(app(app(prim(BRANCH, //
        app(app(prim(BRANCH, data(TRUE)), data(FALSE)), data(TRUE))),
        data(FOUR)), data(FIVE)), //
        "[APP [APP [PRIM BRANCH [APP [APP [PRIM BRANCH [DATA TRUE]] "
            + "[DATA FALSE]] [DATA TRUE]]] [DATA 4]] [DATA 5]]",
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

    public List<?> getListContents() {
      return null;
    }
  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
  }
}
