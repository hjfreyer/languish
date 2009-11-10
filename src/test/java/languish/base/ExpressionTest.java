package languish.base;

import static languish.lambda.Lambda.primitive;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Term;
import languish.testing.CommonExps;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class ExpressionTest extends TestCase {
  public enum Tests implements LanguishTestList {
    LITERAL_INT(primitive(FIVE), //
        "[DATA 5]",
        null,
        FIVE),

    IDENTITY(Lambda.app(IDENT, primitive(FOUR)), //
        "[APP [ABS [REF 1]] [DATA 4]]",
        null,
        FOUR),

    LOOP(CommonExps.LOOP, //
        "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [APP [REF 1] [REF 1]]]]",
        CommonExps.LOOP,
        null),

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
