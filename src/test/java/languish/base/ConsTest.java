package languish.base;

import static languish.lambda.Lambda.*;
import static languish.lang.CommonTest.NULL;
import static languish.testing.TestUtil.FIVE;
import static languish.testing.TestUtil.FOUR;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Term;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

import com.google.common.collect.ImmutableList;

public class ConsTest extends TestCase {
  public enum Tests implements LanguishTestList {
    GET_NULL(NULL, //
        "[DATA []]", null, ImmutableList.of()),

    GET_SINGLETON(cons(primitive(FIVE), NULL), //
        "[CONS [DATA 5] [DATA []]]", null, ImmutableList.of(FIVE)),

    GET_PAIR_WITH_SINGLE_REF(app(abs(cons(ref(1), cons(ref(1), NULL))),
        primitive(FOUR)), //
        "[APP [ABS [CONS [REF 1] [CONS [REF 1] [DATA []]]]] [DATA 4]]", cons(
            primitive(FOUR), cons(primitive(FOUR), NULL)), ImmutableList.of(FOUR, FOUR)),

    GET_PAIR_WITH_DOUBLE_REF(app(app(
        abs(abs(cons(ref(1), cons(ref(2), NULL)))), primitive(FOUR)), primitive(FIVE)), //
        "[APP [APP [ABS [ABS [CONS [REF 1] [CONS [REF 2] [DATA []]]]]] "
            + "[DATA 4]] [DATA 5]]", app(abs(cons(ref(1),
            cons(primitive(FOUR), NULL))), primitive(FIVE)), ImmutableList.of(FIVE, FOUR)), ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final List<?> listContents;

    private Tests(Term expression, String code, Term reducedOnce,
        List<?> listContents) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.listContents = listContents;
    }

    public Term getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Term getReducedOnce() {
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
