package languish.primitives;

import static languish.lambda.Lambda.*;
import static languish.primitives.DataFunctions.ADD;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Term;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class LIntegerTest extends TestCase {

  public static final Term DOUBLE_FUNC = abs(nativeApply(ADD, cons(ref(1), ref(1))));

  public enum Tests implements LanguishTestList {
    TEST_DATA(primitive(FIVE), //
        "[DATA 5]",
        null,
        FIVE),

    TEST_SIMPLE_ADD(nativeApply(ADD, cons(primitive(FIVE), primitive(THREE))),
        "[PRIM [DATA ADD] [CONS [DATA 5] [DATA 3]]]",
        null,
        EIGHT),

    TEST_TRIPLE_ADD(nativeApply(ADD, cons(primitive(FIVE), nativeApply(ADD, cons(primitive(THREE),
        primitive(SIX))))), "[PRIM [DATA ADD] [CONS [DATA 5] "
        + "[PRIM [DATA ADD] [CONS [DATA 3] [DATA 6]]]]]", null, FOURTEEN),

    TEST_DOUBLE(Lambda.app(DOUBLE_FUNC, primitive(FOUR)), //
        "[APP [ABS [PRIM [DATA ADD] [CONS [REF 1] [REF 1]]]] [DATA 4]]",
        nativeApply(ADD, cons(primitive(FOUR), primitive(FOUR))),
        EIGHT),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final LObject reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Term getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Term getReducedOnce() {
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
