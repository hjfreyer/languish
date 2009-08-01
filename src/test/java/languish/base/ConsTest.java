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
    //
    // GET_SINGLETON_WITH_REF(app(abs(cons(ref(1))), data(FOUR)), //
    // "[APP [ABS [CONS [REF 1]]] [DATA 4]]",
    // cons(data(FOUR)),
    // Tuple.of(FOUR)),
    //
    // GET_PAIR_WITH_SINGLE_REF(app(abs(cons(ref(1), ref(1))), data(FOUR)), //
    // "[APP [ABS [CONS [REF 1] [REF 1]]] [DATA 4]]",
    // cons(data(FOUR), data(FOUR)),
    // Tuple.of(FOUR, FOUR)),
    //
    // GET_PAIR_WITH_DOUBLE_REF(app(
    // app(abs(abs(cons(ref(1), ref(2)))), data(FOUR)), data(FIVE)), //
    // "[APP [APP [ABS [ABS [CONS [REF 1] [REF 2]]]] [DATA 4]] [DATA 5]]",
    // app(abs(cons(ref(1), data(FOUR))), data(FIVE)),
    // Tuple.of(FIVE, FOUR)),
    //
    // GET_TRIPLE(app(abs(cons(ref(1), data(FIVE), data(SIX))), data(FOUR)), //
    // "[APP [ABS [CONS [REF 1] [DATA 5] [DATA 6]]] [DATA 4]]",
    // cons(data(FOUR), data(FIVE), data(SIX)),
    // Tuple.of(FOUR, FIVE, SIX)),
    //
    // GET_TRIPLE_WITH_REFS(app(app(app(abs(abs(abs(cons(ref(3), ref(2), ref(1),
    // ref(2))))), data(FOUR)), data(FIVE)), data(SIX)), //
    // "[APP [APP [APP [ABS [ABS [ABS "
    // + "[CONS [REF 3] [REF 2] [REF 1] [REF 2]]]]] "
    // + "[DATA 4]] [DATA 5]] [DATA 6]]",
    // app(
    // app(abs(abs(cons(data(FOUR), ref(2), ref(1), ref(2)))), data(FIVE)),
    // data(SIX)),
    // Tuple.of(FOUR, FIVE, SIX, FIVE)),
    //
    // GET_TRIPLE_WITH_FUNCS_ON_EACH(cons(app(CommonExps.IDENTITY, data(FOUR)),
    // app(CommonExps.IDENTITY, data(FIVE)), app(CommonExps.IDENTITY,
    // data(ONE))), //
    // "[CONS [APP [ABS [REF 1]] [DATA 4]] [APP [ABS [REF 1]] [DATA 5]] "
    // + "[APP [ABS [REF 1]] [DATA 1]]]",
    // cons(data(FOUR), app(CommonExps.IDENTITY, data(FIVE)), app(
    // CommonExps.IDENTITY, data(ONE))),
    // Tuple.of(FOUR, FIVE, ONE)), ;
    ;
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
