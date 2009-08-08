package languish.base;

import static languish.base.Lambda.*;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

import com.hjfreyer.util.Lists;

public class ConsTest extends TestCase {
  public enum Tests implements LanguishTestList {
    GET_NULL(NULL, //
        "[DATA []]",
        null,
        Lists.of()),

    GET_SINGLETON(cons(data(FIVE), NULL), //
        "[CONS [DATA 5] [DATA []]]",
        null,
        Lists.of(FIVE)),

    GET_PAIR_WITH_SINGLE_REF(app(abs(cons(ref(1), cons(ref(1), NULL))),
        data(FOUR)), //
        "[APP [ABS [CONS [REF 1] [CONS [REF 1] [DATA []]]]] [DATA 4]]",
        cons(data(FOUR), cons(data(FOUR), NULL)),
        Lists.of(FOUR, FOUR)),

    GET_PAIR_WITH_DOUBLE_REF(app(app(
        abs(abs(cons(ref(1), cons(ref(2), NULL)))), data(FOUR)), data(FIVE)), //
        "[APP [APP [ABS [ABS [CONS [REF 1] [CONS [REF 2] [DATA []]]]]] "
            + "[DATA 4]] [DATA 5]]",
        app(abs(cons(ref(1), cons(data(FOUR), NULL))), data(FIVE)),
        Lists.of(FIVE, FOUR)), ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final List<?> listContents;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        List<?> listContents) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.listContents = listContents;
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

    public List<?> getListContents() {
      return listContents;
    }

    public LObject getReducedCompletely() {
      return null;
    }
  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
  }
}
