//package languish.lang;
//
//import static languish.testing.CommonExps.*;
//import static languish.testing.TestUtil.*;
//import static languish.util.Lambda.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.TestCase;
//import languish.lambda.LObject;
//import languish.lambda.Term;
//import languish.primitives.DataFunctions;
//import languish.primitives.LBoolean;
//import languish.primitives.LInteger;
//import languish.testing.CommonExps;
//import languish.testing.LanguishTestCase;
//import languish.testing.TestUtil;
//import languish.util.Lambda;
//
//public class CommonTest extends TestCase {
//  public static final Term NULL = primitive(Term.of());
//
//  public static final Term CAAR = abs(car(car(ref(1))));
//  public static final Term CADR = abs(car(cdr(ref(1))));
//
//  public static final Term OMEGA = abs(app(ref(1), ref(1)));
//  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";
//
//  private static final Term LIST_A =
//      cons(primitive(TWO), cons(primitive(THREE), cons(primitive(FIVE), NULL)));
//
//  private static final Term LIST_B =
//      cons(primitive(THREE), cons(primitive(SIX), cons(primitive(TWO), NULL)));
//
//  public enum Tests implements LanguishTestCase {
//    EQ_CODE(EQUALS, //
//        EQUALS_CODE, null, null),
//
//    EQ_INT_IDENTITY(app(app(EQUALS, primitive(TWO)), primitive(TWO)), //
//        null, null, LBoolean.TRUE), EQ_INT_VALUE(app(app(EQUALS, primitive(TWO)),
//        primitive(LInteger.of(2))), null, null, LBoolean.TRUE), EQ_INT_INSIDE_APP(
//        app(app(EQUALS, primitive(TWO)), app(CommonExps.IDENTITY, primitive(LInteger
//            .of(2)))), //
//        null, null, LBoolean.TRUE),
//
//    EQ_INT_RESULT_OF_PRIM(app(app(EQUALS, primitive(FIVE)), nativeApply(DataFunctions.ADD,
//        Lambda.cons(primitive(TWO), primitive(THREE)))), //
//        null, null, LBoolean.TRUE),
//
//    EQ_CONS_BASIC(app(app(EQUALS, cons(primitive(TWO), primitive(THREE))), cons(
//        primitive(TWO), primitive(THREE))), //
//        null, null, LBoolean.TRUE),
//
//    EQ_CONS_WITH_REDUCE(app(app(EQUALS, cons(app(IDENTITY, primitive(TWO)),
//        primitive(THREE))), cons(primitive(TWO),
//        app(IDENTITY, app(IDENTITY, primitive(THREE))))), //
//        null, null, LBoolean.TRUE),
//
//    EQ_CAR_VS_CDAR(app(app(EQUALS, car(LIST_B)), car(cdr(LIST_A))), //
//        null, null, LBoolean.TRUE),
//
//    DNEQ_INT_CONSTANTS(app(app(EQUALS, primitive(TWO)), primitive(THREE)), //
//        null, null, LBoolean.FALSE),
//
//    DNEQ_CONS_BASIC(app(app(EQUALS, cons(primitive(TWO), primitive(FOUR))), cons(
//        primitive(TWO), primitive(THREE))), //
//        null, null, LBoolean.FALSE),
//
//    ;
//
//    private final Term expression;
//    private final String code;
//    private final Term reducedOnce;
//    private final LObject reducedCompletely;
//
//    private Tests(Term expression, String code, Term reducedOnce,
//        LObject reducedCompletely) {
//      this.expression = expression;
//      this.code = code;
//      this.reducedOnce = reducedOnce;
//      this.reducedCompletely = reducedCompletely;
//    }
//
//    public Term getExpression() {
//      return expression;
//    }
//
//    public String getCode() {
//      return code;
//    }
//
//    public Term getReducedOnce() {
//      return reducedOnce;
//    }
//
//    public LObject getReducedCompletely() {
//      return reducedCompletely;
//    }
//
//    public List<?> getListContents() {
//      return null;
//    }
//  }
//
//  public void test() {
//    TestUtil.assertLanguishTestCase(Arrays.asList(Tests.values()));
//  }
//
// }
