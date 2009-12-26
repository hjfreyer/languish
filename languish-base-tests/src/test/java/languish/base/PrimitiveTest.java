package languish.base;

import static languish.base.Terms.primitive;
import static languish.tools.testing.TestUtil.FIVE;
import junit.framework.TestCase;
import languish.tools.testing.LanguishTestCase;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.hjfreyer.util.Tree;

public class PrimitiveTest extends TestCase {
  public enum Tests implements LanguishTestCase {
    PRIMITIVE_INT(primitive(FIVE), //
        "[PRIMITIVE 5 NULL]",
        null,
        PrimitiveTree.from(5)),

    PRIMITIVE_STRING(primitive(new Primitive("FOO")), //
        "[PRIMITIVE \"FOO\" NULL]",
        null,
        PrimitiveTree.from("FOO")),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final Tree<Primitive> reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        Tree<Primitive> reducedCompletely) {
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

    public Tree<Primitive> getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
