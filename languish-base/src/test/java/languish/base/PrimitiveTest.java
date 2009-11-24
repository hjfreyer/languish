package languish.base;

import static languish.base.Terms.primitive;
import static languish.testing.TestUtil.FIVE;
import junit.framework.TestCase;
import languish.base.Primitive;
import languish.base.Term;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.PrimitiveTree;

public class PrimitiveTest extends TestCase {
  public enum Tests implements LanguishTestCase {
    PRIMITIVE_INT(primitive(FIVE), //
        "[PRIMITIVE 5 NULL]",
        null,
        PrimitiveTree.copyOf(5)),

    PRIMITIVE_STRING(primitive(new Primitive("FOO")), //
        "[PRIMITIVE \"FOO\" NULL]",
        null,
        PrimitiveTree.copyOf("FOO")),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final PrimitiveTree reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        PrimitiveTree reducedCompletely) {
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

    public PrimitiveTree getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
