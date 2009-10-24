package languish.base;

import static languish.base.Lambda.abs;
import static languish.base.Lambda.app;
import static languish.base.Lambda.cons;
import static languish.base.Lambda.data;
import static languish.base.Lambda.ref;
import static languish.lang.CommonTest.NULL;
import static languish.testing.TestUtil.FIVE;
import static languish.testing.TestUtil.FOUR;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

import com.google.common.collect.ImmutableList;

public class ConsTest extends TestCase {
  public enum Tests implements LanguishTestList {
    GET_NULL(NULL, //
        "[DATA []]", null, ImmutableList.of()),

    GET_SINGLETON(cons(data(FIVE), NULL), //
        "[CONS [DATA 5] [DATA []]]", null, ImmutableList.of(FIVE)),

    GET_PAIR_WITH_SINGLE_REF(app(abs(cons(ref(1), cons(ref(1), NULL))),
        data(FOUR)), //
        "[APP [ABS [CONS [REF 1] [CONS [REF 1] [DATA []]]]] [DATA 4]]", cons(
            data(FOUR), cons(data(FOUR), NULL)), ImmutableList.of(FOUR, FOUR)),

    GET_PAIR_WITH_DOUBLE_REF(app(app(
        abs(abs(cons(ref(1), cons(ref(2), NULL)))), data(FOUR)), data(FIVE)), //
        "[APP [APP [ABS [ABS [CONS [REF 1] [CONS [REF 2] [DATA []]]]]] "
            + "[DATA 4]] [DATA 5]]", app(abs(cons(ref(1),
            cons(data(FOUR), NULL))), data(FIVE)), ImmutableList.of(FIVE, FOUR)), ;

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
