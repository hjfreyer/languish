package languish.lambda;

import static languish.testing.TestUtil.*;
import static languish.util.Lambda.*;
import junit.framework.TestCase;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.JavaWrapper;
import languish.util.Lambda;

public class AbstractionTest extends TestCase {
  public enum Tests implements LanguishTestCase {
    BASIC_APPLY( // 
        app(IDENT, primitive(FIVE)),
        "[APP [ABS [REF 1 NULL] NULL] [PRIMITIVE 5 NULL]]",
        primitive(FIVE),
        JavaWrapper.of(5)),

    ARGUMENT_CHOOSER_1( //
        app(app(TRUE, primitive(FOUR)), primitive(FIVE)),
        "[APP [APP [ABS [ABS [REF 2 NULL] NULL] NULL] "
            + "[PRIMITIVE 4 NULL]] [PRIMITIVE 5 NULL]]",
        app(abs(primitive(FOUR)), primitive(FIVE)),
        JavaWrapper.of(4)),

    ARGUMENT_CHOOSER_2( //
        app(app(Lambda.FALSE, primitive(TestUtil.FOUR)), primitive(FIVE)),
        "[APP [APP [ABS [ABS [REF 1 NULL] NULL] NULL] [PRIMITIVE 4 NULL]] "
            + "[PRIMITIVE 5 NULL]]",
        app(abs(ref(1)), primitive(FIVE)),
        JavaWrapper.of(5)),

    NON_HALTER( //
        TestUtil.LOOP,
        "[APP [ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL] "
            + "[ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL]]",
        TestUtil.LOOP,
        null),

    IRRELEVANT_NON_HALTER( //
        app(app(Lambda.TRUE, primitive(TestUtil.FOUR)), TestUtil.LOOP),
        "[APP [APP [ABS [ABS [REF 2 NULL] NULL] NULL] [PRIMITIVE 4 NULL]] "
            + "[APP [ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL] "
            + "[ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL]]]",
        app(abs(primitive(FOUR)), TestUtil.LOOP),
        JavaWrapper.of(4)),

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
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
//

//
// public void testRelevantNonHalterFunction() {
// Term exp =
// TestUtil.reduceTupleOnce(Lambda.app(Lambda.app(SECOND_PICKER, Lambda
// .primitive(FOUR)), LOOP));
//
// assertEquals(LOOP, TestUtil.reduceTupleOnce(exp));
// }
//
// public void testNonHalter() {
// assertEquals(LOOP, TestUtil.reduceTupleOnce(LOOP));
// }
// }
