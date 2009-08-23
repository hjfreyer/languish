package languish.libtesting;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.OMEGA;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.primitives.DataFunctions;
import languish.testing.CommonExps;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class ListsTest extends TestCase {

  public static final Tuple EMPTY_LIST = data(Tuple.of());
  public static final String EMPTY_LIST_CODE = "[DATA []]";

  public static final Tuple PUSHL = abs(abs(cons(ref(1), ref(2))));
  public static final String PUSHL_CODE = "[ABS [ABS [CONS [REF 1] [REF 2]]]]";

  public static final Tuple ADDL =
      app(OMEGA, // ========= RECURSIVE
          abs( // ============= SELF
          abs( // ============= LIST
          abs( // ============= ITEM
          app(app(prim(DataFunctions.BRANCH, app(app(CommonExps.EQUALS,
              EMPTY_LIST), ref(2))), // =============
              // If
              // (LIST
              // == [])
              cons(ref(1), EMPTY_LIST)), // Then: return [ITEM []]
              cons(car(ref(2)), // === Else, return (CAR LIST) ::
                  // ======================== ADDL(ADDL, (CDR LIST), ITEM)
                  app(app(app(ref(3), ref(3)), cdr(ref(2))), ref(1))))))));

  public static final String ADDL_CODE =
      "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [APP [APP [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]] [DATA []]] [REF 2]]] [CONS [REF 1] [DATA []]]] [CONS [CAR [REF 2]] [APP [APP [APP [REF 3] [REF 3]] [CDR [REF 2]]] [REF 1]]]]]]]]";

  public enum Tests implements LanguishTestList {
    PL_CODE(PUSHL, //
        PUSHL_CODE,
        null,
        null),

    AL_CODE(ADDL, //
        ADDL_CODE,
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
