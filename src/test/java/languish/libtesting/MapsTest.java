package languish.libtesting;

import static languish.base.Lambda.*;
import static languish.libtesting.CommonTest.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.primitives.DataFunctions;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class MapsTest extends TestCase {

  public static final Tuple MAP_PUT = //
      abs(abs(abs( // MAP, KEY, VAL
      cons(cons(ref(2), ref(1)), ref(3)))));
  public static final String MAP_PUT_CODE =
      "[ABS [ABS [ABS [CONS [CONS [REF 2] [REF 1]] [REF 3]]]]]";

  public static final Tuple MAP_GET = //
      app(OMEGA, // recursive
          abs(abs(abs(abs( // SELF, MAP, KEY, DEFAULT
          app(app(prim(DataFunctions.BRANCH, // IF
              prim(DataFunctions.EQUALS, cons(NULL, ref(3)))), // MAP ==
              // NULL
              ref(1)), // RETURN DEFAULT, ELSE
              app(app(prim(DataFunctions.BRANCH, // IF
                  // top of map key = KEY
                  prim(DataFunctions.EQUALS, cons(ref(2), app(CAAR, ref(3))))),
                  cdr(car(ref(3)))), // then return top of map val
                  app(app(app(app( // ELSE, recurse
                      ref(4), //
                      ref(4)), //
                      cdr(ref(3))), //
                      ref(2)), //
                      ref(1)))))))));

  public static final String MAP_GET_CODE =
      "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA EQUALS] [CONS [DATA []] [REF 3]]]] [REF 1]] [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA EQUALS] [CONS [REF 2] [APP [ABS [CAR [CAR [REF 1]]]] [REF 3]]]]] [CDR [CAR [REF 3]]]] [APP [APP [APP [APP [REF 4] [REF 4]] [CDR [REF 3]]] [REF 2]] [REF 1]]]]]]]]]";

  public enum Tests implements LanguishTestList {
    MP_CODE(MAP_PUT, //
        MAP_PUT_CODE,
        null,
        null),

    MG_CODE(MAP_GET, //
        MAP_GET_CODE,
        null,
        null),

    ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final LObject reducedCompletely;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Tuple getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Tuple getReducedOnce() {
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
