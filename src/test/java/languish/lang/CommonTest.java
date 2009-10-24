package languish.lang;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.*;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;
import languish.testing.CommonExps;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class CommonTest extends TestCase {
  public static final Tuple NULL = data(Tuple.of());

  public static final Tuple CAAR = abs(car(car(ref(1))));
  public static final Tuple CADR = abs(car(cdr(ref(1))));

  public static final Tuple OMEGA = abs(app(ref(1), ref(1)));
  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";

  private static final Tuple LIST_A =
      cons(data(TWO), cons(data(THREE), cons(data(FIVE), NULL)));

  private static final Tuple LIST_B =
      cons(data(THREE), cons(data(SIX), cons(data(TWO), NULL)));

  public enum Tests implements LanguishTestList {
    EQ_CODE(EQUALS, //
        EQUALS_CODE, null, null),

    EQ_INT_IDENTITY(app(app(EQUALS, data(TWO)), data(TWO)), //
        null, null, LBoolean.TRUE), EQ_INT_VALUE(app(app(EQUALS, data(TWO)),
        data(LInteger.of(2))), null, null, LBoolean.TRUE), EQ_INT_INSIDE_APP(
        app(app(EQUALS, data(TWO)), app(CommonExps.IDENTITY, data(LInteger
            .of(2)))), //
        null, null, LBoolean.TRUE),

    EQ_INT_RESULT_OF_PRIM(app(app(EQUALS, data(FIVE)), prim(DataFunctions.ADD,
        Lambda.cons(data(TWO), data(THREE)))), //
        null, null, LBoolean.TRUE),

    EQ_CONS_BASIC(app(app(EQUALS, cons(data(TWO), data(THREE))), cons(
        data(TWO), data(THREE))), //
        null, null, LBoolean.TRUE),

    EQ_CONS_WITH_REDUCE(app(app(EQUALS, cons(app(IDENTITY, data(TWO)),
        data(THREE))), cons(data(TWO),
        app(IDENTITY, app(IDENTITY, data(THREE))))), //
        null, null, LBoolean.TRUE),

    EQ_CAR_VS_CDAR(app(app(EQUALS, car(LIST_B)), car(cdr(LIST_A))), //
        null, null, LBoolean.TRUE),

    DNEQ_INT_CONSTANTS(app(app(EQUALS, data(TWO)), data(THREE)), //
        null, null, LBoolean.FALSE),

    DNEQ_CONS_BASIC(app(app(EQUALS, cons(data(TWO), data(FOUR))), cons(
        data(TWO), data(THREE))), //
        null, null, LBoolean.FALSE),

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
