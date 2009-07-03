//package languish.prim.data;
//
//import static languish.testing.TestConstants.*;
//
//import java.util.Arrays;
//
//import junit.framework.TestCase;
//import languish.lambda.Application;
//import languish.lambda.Tuple;
//import languish.testing.ExpressionTester;
//import languish.testing.ExpressionToTest;
//
//public class LCompositeTest extends TestCase {
//
//  private static final LComposite PAIR =
//      LComposite.of(new LObject[] { FOUR, FIVE });
//
//  private static final LComposite TWO_DEEP =
//      LComposite.of(new LObject[] { PAIR, SIX });
//
//  public enum Tests implements ExpressionToTest {
//
//    FUNCTION_EQUALITY(LComposites.GET_ELEMENT, //
//        "(~GET_ELEMENT~)",
//        null,
//        null),
//
//    FUNCTION_EQUALITY_2(LComposites.WRAP, //
//        "(~WRAP~)",
//        null,
//        null),
//
//    SIMPLE_GET(Application.of(Application.of(LComposites.GET_ELEMENT, w(PAIR)),
//        w(ZERO)), //
//        "(APP (APP (~GET_ELEMENT~) "
//            + "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))) (!0!))",
//        null,
//        FOUR),
//
//    SIMPLE_GET_OTHER(Application.of(Application.of(LComposites.GET_ELEMENT,
//        w(PAIR)), w(ONE)), //
//        "(APP (APP (~GET_ELEMENT~) "
//            + "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))) (!1!))",
//        null,
//        FIVE),
//
//    SIMPLE_GET_APPLICATION(Application.of(Application.of(
//        LComposites.GET_ELEMENT, w(PAIR)), Application.of(Application.of(
//        LIntegers.ADD, w(ZERO)), w(ONE))), //
//        "(APP (APP (~GET_ELEMENT~) "
//            + "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))) "
//            + "(APP (APP (~ADD~) (!0!)) (!1!)))",
//        null,
//        FIVE),
//
//    TWO_DEEP_GET(Application.of(Application.of(LComposites.GET_ELEMENT,
//        w(TWO_DEEP)), w(ZERO)), //
//        "(APP (APP (~GET_ELEMENT~) (APP (APP (APP (~WRAP~) (!2!)) "
//            + "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))) (!6!))) (!0!))",
//        null,
//        PAIR),
//
//    TWO_DEEP_GET_TWICE(Application.of(Application.of(LComposites.GET_ELEMENT,
//        Application.of(Application.of(LComposites.GET_ELEMENT, w(TWO_DEEP)),
//            w(ZERO))), w(ONE)), //
//        "(APP (APP (~GET_ELEMENT~) "
//            + "(APP (APP (~GET_ELEMENT~) (APP (APP (APP (~WRAP~) (!2!)) "
//            + "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))) (!6!))) (!0!))) "
//            + "(!1!))",
//        null,
//        FIVE),
//
//    WRAP_TEST_EMPTY(Application.of(LComposites.WRAP, w(ZERO)),
//        "(APP (~WRAP~) (!0!))",
//        null,
//        new LComposite(0)),
//
//    WRAP_TEST_SIMPLE(Application.of(Application.of(Application.of(
//        LComposites.WRAP, w(TWO)), w(FOUR)), w(FIVE)),
//        "(APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!))",
//        null,
//        PAIR),
//
//    WRAP_TEST_TWO_DEEP(Application.of(Application.of(Application.of(
//        LComposites.WRAP, w(TWO)), WRAP_TEST_SIMPLE.expression), w(SIX)), //
//        "(APP (APP (APP (~WRAP~) (!2!)) " + WRAP_TEST_SIMPLE.code + ") (!6!))",
//        null,
//        TWO_DEEP),
//
//    WRAP_TEST_FUNCTION(Application.of(Application.of(Application.of(
//        LComposites.WRAP, w(TWO)), Application.of(IDENT, w(FOUR))), Application
//        .of(Application.of(LIntegers.ADD, w(TWO)), w(THREE))), //
//        "(APP (APP (APP (~WRAP~) (!2!)) (APP (ABS +1) (!4!))) "
//            + "(APP (APP (~ADD~) (!2!)) (!3!)))",
//        null,
//        PAIR), ;
//
//    private final Tuple expression;
//    private final String code;
//    private final Tuple reducedOnce;
//    private final LObject reducedCompletely;
//
//    private Tests(Tuple expression, String code, Tuple reducedOnce,
//        LObject reducedCompletely) {
//      this.expression = expression;
//      this.code = code;
//      this.reducedOnce = reducedOnce;
//      this.reducedCompletely = reducedCompletely;
//    }
//
//    public Tuple getExpression() {
//      return expression;
//    }
//
//    public String getCode() {
//      return code;
//    }
//
//    public Tuple getReducedOnce() {
//      return reducedOnce;
//    }
//
//    public LObject getReducedCompletely() {
//      return reducedCompletely;
//    }
//  }
//
//  public void test() {
//    ExpressionTester.testExpressions(Arrays.asList(Tests.values()));
//  }
//}
