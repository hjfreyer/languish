//package languish.prim.data;
//
//import static languish.testing.TestConstants.*;
//
//import java.util.Arrays;
//
//import junit.framework.TestCase;
//import languish.lambda.Application;
//import languish.lambda.Expression;
//import languish.lambda.Data;
//import languish.testing.ExpressionTester;
//import languish.testing.ExpressionToTest;
//
//public class LMapTest extends TestCase {
//
//  public enum Tests implements ExpressionToTest {
//
//    EMPTY_MAP_EQUALITY(Data.of(LMaps.EMPTY_MAP), //
//        "(~EMPTY_MAP~)",
//        null,
//        LMaps.EMPTY_MAP),
//
//    // PUT
//    PUT_SINGLE_ELEMENT(Application.of(Application.of(Application.of(
//        LMaps.PUT_MAP, w(LMaps.EMPTY_MAP)), w(LSymbol.of("FIVE"))), w(FIVE)), //
//        "(APP (APP (APP (~PUT_MAP~) (~EMPTY_MAP~)) (!\"FIVE\"!)) (!5!))",
//        null,
//        LMap.of().put("FIVE", FIVE)),
//
//    PUT_DOUBLE_ELEMENT(Application.of(Application.of(Application.of(
//        LMaps.PUT_MAP, PUT_SINGLE_ELEMENT.expression), w(LSymbol.of("FOUR"))),
//        w(FOUR)), //
//        "(APP (APP (APP (~PUT_MAP~) " + PUT_SINGLE_ELEMENT.code
//            + ") (!\"FOUR\"!)) (!4!))",
//        null,
//        LMap.of().put("FIVE", FIVE).put("FOUR", FOUR)),
//
//    PUT_OVERWRITE(Application.of(Application.of(Application.of(LMaps.PUT_MAP,
//        PUT_SINGLE_ELEMENT.expression), w(LSymbol.of("FIVE"))), w(FOUR)),
//        "(APP (APP (APP (~PUT_MAP~) " + PUT_SINGLE_ELEMENT.code
//            + ") (!\"FIVE\"!)) (!4!))",
//        null,
//        LMap.of().put("FIVE", FOUR)),
//
//    // GET
//
//    ;
//
//    private final Expression expression;
//    private final String code;
//    private final Expression reducedOnce;
//    private final LObject reducedCompletely;
//
//    private Tests(Expression expression, String code, Expression reducedOnce,
//        LObject reducedCompletely) {
//      this.expression = expression;
//      this.code = code;
//      this.reducedOnce = reducedOnce;
//      this.reducedCompletely = reducedCompletely;
//    }
//
//    public Expression getExpression() {
//      return expression;
//    }
//
//    public String getCode() {
//      return code;
//    }
//
//    public Expression getReducedOnce() {
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
