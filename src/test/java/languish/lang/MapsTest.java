//package languish.lang;
//
//import static languish.lambda.Term.NULL;
//import static languish.testing.TestUtil.*;
//import static languish.util.Lambda.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.TestCase;
//import languish.lambda.Term;
//import languish.primitives.DataFunctions;
//import languish.primitives.LInteger;
//import languish.primitives.LSymbol;
//import languish.testing.LanguishTestCase;
//import languish.testing.TestUtil;
//
//import com.google.common.collect.ImmutableList;
//
//public class MapsTest extends TestCase {
//
//  public static final Term MAP_PUT = //
//      abs(abs(abs( // MAP, KEY, VAL
//      cons(cons(ref(2), cons(ref(1), NULL)), ref(3)))));
//  public static final String MAP_PUT_CODE =
//      "[ABS [ABS [ABS [CONS [CONS [REF 2] [CONS [REF 1] [DATA []]]] [REF 3]]]]]";
//
//  public static final Term MAP_GET = //
//      app(OMEGA, // recursive
//          abs(abs(abs(abs( // SELF, MAP, KEY, DEFAULT
//          app(app(nativeApply(DataFunctions.BRANCH, // IF
//              eq(NULL, ref(3))), // MAP == NULL
//              ref(1)), // RETURN DEFAULT, ELSE
//              app(app(nativeApply(DataFunctions.BRANCH, // IF
//                  // top of map key = KEY
//                  eq(ref(2), app(CAAR, ref(3)))), car(cdr(car(ref(3))))), // then
//                  // return
//                  // top of
//                  // map val
//                  app(app(app(app( // ELSE, recurse
//                      ref(4), //
//                      ref(4)), //
//                      cdr(ref(3))), //
//                      ref(2)), //
//                      ref(1)))))))));
//
//  public static final String MAP_GET_CODE =
//      "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [APP [APP [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]] [DATA []]] [REF 3]]] [REF 1]] [APP [APP [PRIM [DATA BRANCH] [APP [APP [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]] [REF 2]] [APP [ABS [CAR [CAR [REF 1]]]] [REF 3]]]] [CAR [CDR [CAR [REF 3]]]]] [APP [APP [APP [APP [REF 4] [REF 4]] [CDR [REF 3]]] [REF 2]] [REF 1]]]]]]]]]";
//
//  @SuppressWarnings("unchecked")
//  public enum Tests implements LanguishTestCase {
//    MP_CODE(MAP_PUT, //
//        MAP_PUT_CODE,
//        null,
//        null,
//        null),
//
//    MG_CODE(MAP_GET, //
//        MAP_GET_CODE,
//        null,
//        null,
//        null),
//
//    // PUT
//    PUT_SINGLE_ELEMENT(app(app(app(MAP_PUT, NULL),
//        primitive(LSymbol.of("FIVE"))), primitive(FIVE)), //
//        "[APP [APP [APP " + MAP_PUT_CODE
//            + " [DATA []]] [DATA \"FIVE\"]] [DATA 5]]",
//        null,
//        null,
//        ImmutableList.of(ImmutableList.of(LSymbol.of("FIVE"), FIVE))),
//
//    PUT_DOUBLE_ELEMENT(app(app(app(MAP_PUT, PUT_SINGLE_ELEMENT.expression),
//        primitive(LSymbol.of("FOUR"))), primitive(FOUR)), //
//        "[APP [APP [APP " + MAP_PUT_CODE + " " + PUT_SINGLE_ELEMENT.code
//            + "] [DATA \"FOUR\"]] [DATA 4]]",
//        null,
//        null,
//        ImmutableList.of(ImmutableList.of(LSymbol.of("FOUR"), FOUR),
//            ImmutableList.of(LSymbol.of("FIVE"), FIVE))),
//
//    PUT_OVERWRITE(app(app(app(MAP_PUT, PUT_DOUBLE_ELEMENT.expression),
//        primitive(LSymbol.of("FIVE"))), primitive(THREE)), //
//        "[APP [APP [APP " + MAP_PUT_CODE + " " + PUT_DOUBLE_ELEMENT.code
//            + "] [DATA \"FIVE\"]] [DATA 3]]",
//        null,
//        null,
//        ImmutableList.of(ImmutableList.of(LSymbol.of("FIVE"), THREE),
//            ImmutableList.of(LSymbol.of("FOUR"), FOUR), ImmutableList.of(
//                LSymbol.of("FIVE"), FIVE))),
//
//    // GET
//    GET_FROM_EMPTY(app(app(app(MAP_GET, NULL), primitive(LSymbol.of("FOO"))),
//        primitive(LSymbol.of("DEFAULT"))), //
//        "[APP [APP [APP " + MAP_GET_CODE + " [DATA []]] [DATA \"FOO\"]] "
//            + "[DATA \"DEFAULT\"]]",
//        null,
//        LSymbol.of("DEFAULT"),
//        null),
//
//    GET_FROM_SINGLETON_CORRECT(app(app(
//        app(MAP_GET, convertJavaObjectToTerm(ImmutableList.of(ImmutableList.of(
//            "FIVE", 5)))), primitive(LSymbol.of("FIVE"))), primitive(LSymbol
//        .of("DEFAULT"))), //
//        "[APP [APP [APP " + MAP_GET_CODE
//            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
//            + "[DATA []]]] [DATA \"FIVE\"]] [DATA \"DEFAULT\"]]",
//        null,
//        LInteger.of(5),
//        null),
//
//    GET_FROM_SINGLETON_INCORRECT(app(app(
//        app(MAP_GET, convertJavaObjectToTerm(ImmutableList.of(ImmutableList.of(
//            "FIVE", 5)))), primitive(LSymbol.of("BAD"))), primitive(LSymbol
//        .of("DEFAULT"))), //
//        "[APP [APP [APP " + MAP_GET_CODE
//            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
//            + "[DATA []]]] [DATA \"BAD\"]] [DATA \"DEFAULT\"]]",
//        null,
//        LSymbol.of("DEFAULT"),
//        null),
//
//    GET_FROM_DOUBLE_A(app(app(app(MAP_GET,
//        convertJavaObjectToTerm(ImmutableList.of(ImmutableList.of("FIVE", 5),
//            ImmutableList.of("FOUR", 4)))), primitive(LSymbol.of("FIVE"))),
//        primitive(LSymbol.of("DEFAULT"))), //
//        "[APP [APP [APP "
//            + MAP_GET_CODE
//            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
//            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] [DATA []]]]] "
//            + "[DATA \"FIVE\"]] [DATA \"DEFAULT\"]]",
//        null,
//        LInteger.of(5),
//        null),
//
//    GET_FROM_DOUBLE_B(app(app(app(MAP_GET,
//        convertJavaObjectToTerm(ImmutableList.of(ImmutableList.of("FIVE", 5),
//            ImmutableList.of("FOUR", 4)))), primitive(LSymbol.of("FOUR"))),
//        primitive(LSymbol.of("DEFAULT"))), //
//        "[APP [APP [APP "
//            + MAP_GET_CODE
//            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
//            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] [DATA []]]]] "
//            + "[DATA \"FOUR\"]] [DATA \"DEFAULT\"]]",
//        null,
//        LInteger.of(4),
//        null),
//
//    GET_FROM_OVERWRITE(app(app(app(MAP_GET, convertJavaObjectToTerm(JavaWrapper
//        .of(ImmutableList.of(ImmutableList.of("FOUR", 3), ImmutableList.of(
//            "FIVE", 5), ImmutableList.of("FOUR", 4))))), primitive(LSymbol
//        .of("FOUR"))), primitive(LSymbol.of("DEFAULT"))), //
//        "[APP [APP [APP " + MAP_GET_CODE + " [CONS "
//            + "[CONS [DATA \"FOUR\"] [CONS [DATA 3] [DATA []]]] "
//            + "[CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
//            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] "
//            + "[DATA []]]]]] [DATA \"FOUR\"]] [DATA \"DEFAULT\"]]",
//        null,
//        LInteger.of(3),
//        null),
//
//    STORE_ABS(app(app(app(MAP_PUT, NULL), primitive(LSymbol.of("ADD_ONE"))),
//        abs(nativeApply(DataFunctions.ADD, cons(ref(1), primitive(LInteger
//            .of(1)))))), //
//        "[APP [APP [APP " + MAP_PUT_CODE + " [DATA []]] "
//            + "[DATA \"ADD_ONE\"]] "
//            + "[ABS [PRIM [DATA ADD] [CONS [REF 1] [DATA 1]]]]]",
//        null,
//        null,
//        null),
//
//    LOAD_ABS(app(
//        //
//        app(app(app(MAP_GET, STORE_ABS.expression), primitive(LSymbol
//            .of("ADD_ONE"))), primitive(LSymbol.of("DEFAULT"))),
//        primitive(LInteger.of(8))), //
//        "[APP [APP [APP [APP " + MAP_GET_CODE + " " + STORE_ABS.code + "] "
//            + "[DATA \"ADD_ONE\"]] " + "[DATA \"DEFAULT\"]] " + "[DATA 8]]",
//        null,
//        NINE,
//        null),
//
//    ;
//
//    private final Term expression;
//    private final String code;
//    private final Term reducedOnce;
//    private final LObject reducedCompletely;
//    private final List<?> listContents;
//
//    private Tests(Term expression, String code, Term reducedOnce,
//        LObject reducedCompletely, List<?> listContents) {
//      this.expression = expression;
//      this.code = code;
//      this.reducedOnce = reducedOnce;
//      this.reducedCompletely = reducedCompletely;
//      this.listContents = listContents;
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
//      return listContents;
//    }
//
//  }
//
//  public void test() {
//    TestUtil.assertLanguishTestCase(Arrays.asList(Tests.values()));
//  }
//
//  private static Term eq(Term a, Term b) {
//    return app(app(CommonExps.EQUALS, a), b);
//  }
//
// }
