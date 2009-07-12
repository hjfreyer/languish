package languish.primitives;

import static languish.base.Lambda.*;
import static languish.primitives.DataFunctions.ADD;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class LIntegerTest extends TestCase {

  public static final Tuple DOUBLE_FUNC = abs(prim(ADD, ref(1), ref(1)));

  public enum Tests implements LanguishTestList {
    // IDENITY TESTS
    TEST_DATA(data(FIVE), //
        "[DATA 5]",
        null,
        FIVE),

    TEST_SIMPLE_ADD(prim(ADD, data(FIVE), data(THREE)),
        "[PRIM ADD [DATA 5] [DATA 3]]",
        null,
        EIGHT),

    TEST_DOUBLE(Lambda.app(DOUBLE_FUNC, data(FOUR)), //
        "[APP [ABS [PRIM ADD [REF 1] [REF 1]]] [DATA 4]]",
        prim(ADD, data(FOUR), data(FOUR)),
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

    public List<?> getListContents() {
      return null;
    }
  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
  }
}
