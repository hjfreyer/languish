package languish.lambda;

import static languish.testing.TestUtil.FIVE;
import static languish.util.Lambda.primitive;

import java.util.List;

import junit.framework.TestCase;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.JavaWrapper;

public class ExpressionTest extends TestCase {
  public enum Tests implements LanguishTestCase {
    LITERAL_INT(primitive(FIVE), //
        "[PRIMITIVE 5 NULL]",
        null,
        JavaWrapper.of(5)),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final JavaWrapper reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        JavaWrapper reducedCompletely) {
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

    public JavaWrapper getReducedCompletely() {
      return reducedCompletely;
    }

    public List<?> getListContents() {
      return null;
    }
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
