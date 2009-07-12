package languish.base;

import static languish.base.Lambda.data;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.testing.CommonExps;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class ExpressionTest extends TestCase {
  public enum Tests implements LanguishTestList {
    LITERAL_INT(data(FIVE), //
        "[DATA 5]",
        null,
        FIVE),

    IDENTITY(Lambda.app(IDENT, data(FOUR)), //
        "[APP [ABS [REF 1]] [DATA 4]]",
        null,
        FOUR),

    LOOP(CommonExps.LOOP, //
        "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [APP [REF 1] [REF 1]]]]",
        CommonExps.LOOP,
        null),

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
