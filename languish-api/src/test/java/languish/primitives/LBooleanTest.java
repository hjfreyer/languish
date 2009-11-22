//package languish.primitives;
//
//import static languish.primitives.DataFunctions.*;
//import static languish.primitives.LBoolean.*;
//import static languish.testing.CommonExps.LOOP;
//import static languish.testing.TestUtil.*;
//import static languish.util.Lambda.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.TestCase;
//import languish.lambda.LObject;
//import languish.lambda.Term;
//import languish.testing.LanguishTestCase;
//import languish.testing.TestUtil;
//
//public class LBooleanTest extends TestCase {
//  public enum Tests implements LanguishTestCase {
//    // IDENITY TESTS
//    GET_TRUE(primitive(TRUE), //
//        "[DATA TRUE]",
//        null,
//        TRUE),
//
//    GET_FALSE(primitive(FALSE), //
//        "[DATA FALSE]",
//        null,
//        FALSE),
//
//    // BASIC TESTS
//    LITERAL_TRUE(nativeApply(BRANCH, primitive(TRUE)), //
//        "[PRIM [DATA BRANCH] [DATA TRUE]]",
//        BRANCH_THEN,
//        null),
//
//    LITERAL_FALSE(nativeApply(BRANCH, primitive(FALSE)), //
//        "[PRIM [DATA BRANCH] [DATA FALSE]]",
//        BRANCH_ELSE,
//        null),
//
//    APPLICATION_ON_SELECTOR(nativeApply(BRANCH, app(IDENT, primitive(TRUE))),
//        "[PRIM [DATA BRANCH] [APP [ABS [REF 1]] [DATA TRUE]]]",
//        LITERAL_TRUE.expression,
//        null),
//
//    LOOP_ON_SELECTOR(nativeApply(BRANCH, LOOP),
//        "[PRIM [DATA BRANCH] [APP [ABS [APP [REF 1] [REF 1]]] "
//            + "[ABS [APP [REF 1] [REF 1]]]]]",
//        nativeApply(BRANCH, LOOP),
//        null),
//
//    // BRANCH TESTS
//    BASIC_BRANCH_THEN(app(app(BRANCH_THEN, primitive(FIVE)), primitive(FOUR)), //
//        "[APP [APP [ABS [ABS [REF 2]]] [DATA 5]] [DATA 4]]",
//        app(abs(primitive(LInteger.of(5))), primitive(LInteger.of(4))),
//        FIVE),
//
//    BASIC_BRANCH_ELSE(app(app(BRANCH_ELSE, primitive(FIVE)), primitive(FOUR)), //
//        "[APP [APP [ABS [ABS [REF 1]]] [DATA 5]] [DATA 4]]",
//        app(abs(ref(1)), primitive(LInteger.of(4))),
//        FOUR),
//
//    LOOP_ON_BRANCH(app(app(BRANCH_THEN, LOOP), primitive(FIVE)), //
//        "[APP [APP [ABS [ABS [REF 2]]] [APP [ABS [APP [REF 1] [REF 1]]] "
//            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
//        app(abs(LOOP), primitive(LInteger.of(5))),
//        null),
//
//    LOOP_ON_BRANCH_2(LOOP_ON_BRANCH.reducedOnce, //
//        "[APP [ABS [APP [ABS [APP [REF 1] [REF 1]]] "
//            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
//        LOOP,
//        null),
//
//    LOOP_OFF_BRANCH(app(app(BRANCH_ELSE, LOOP), primitive(FIVE)), //
//        "[APP [APP [ABS [ABS [REF 1]]] [APP [ABS [APP [REF 1] [REF 1]]] "
//            + "[ABS [APP [REF 1] [REF 1]]]]] [DATA 5]]",
//        app(abs(ref(1)), primitive(LInteger.of(5))),
//        FIVE),
//
//    // OVERALL
//    WHOLE_SHEBANG(app(app(nativeApply(BRANCH, primitive(TRUE)), app(IDENT, primitive(FOUR))),
//        primitive(FIVE)), //
//        "[APP [APP [PRIM [DATA BRANCH] [DATA TRUE]] "
//            + "[APP [ABS [REF 1]] [DATA 4]]] [DATA 5]]",
//        app(app(abs(abs(ref(2))), app(abs(ref(1)), primitive(LInteger.of(4)))),
//            primitive(LInteger.of(5))),
//        FOUR),
//
//    NESTED(app(app(nativeApply(BRANCH, //
//        app(app(nativeApply(BRANCH, primitive(TRUE)), primitive(FALSE)), primitive(TRUE))),
//        primitive(FOUR)), primitive(FIVE)), //
//        "[APP [APP [PRIM [DATA BRANCH] [APP [APP [PRIM [DATA BRANCH] "
//            + "[DATA TRUE]] "
//            + "[DATA FALSE]] [DATA TRUE]]] [DATA 4]] [DATA 5]]",
//        app(app(nativeApply(BRANCH,
//            app(app(abs(abs(ref(2))), primitive(FALSE)), primitive(TRUE))), primitive(FOUR)),
//            primitive(FIVE)),
//        FIVE),
//
//    ;
//
//    private final Term expression;
//    private final String code;
//    private final Term reducedOnce;
//    private final JavaWrapper reducedCompletely;
//
//    private Tests(Term expression, String code, Term reducedOnce,
//        JavaWrapper reducedCompletely) {
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
//    public JavaWrapper getReducedCompletely() {
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
// }
