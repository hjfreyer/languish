package languish.prim.data;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.*;
import static languish.testing.TestUtil.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class ListTest extends TestCase {

  public enum Tests implements ExpressionToTest {
    // IDENITY TESTS
    TEST_EMPTY_LIST(data(Tuple.of()), //
        "[DATA []]",
        null,
        Tuple.of()),

    SINGLE_ELEMENT_LIST(cons(data(TWO), data(Tuple.of())), //
        "[CONS [DATA 2] [DATA []]]",
        data(Tuple.of(TWO, Tuple.of())),
        Tuple.of(TWO, Tuple.of())),

    DOUBLE_ELEMENT_LIST(cons(data(THREE), cons(data(TWO), data(Tuple.of()))), //
        "[CONS [DATA 3] [CONS [DATA 2] [DATA []]]]",
        cons(data(THREE), data(Tuple.of(TWO, Tuple.of()))),
        Tuple.of(THREE, Tuple.of(TWO, Tuple.of()))),

    TRIPLE_ELEMENT_LIST(cons(data(THREE), cons(data(FOUR), cons(data(TWO),
        data(Tuple.of())))), //
        "[CONS [DATA 3] [CONS [DATA 4] [CONS [DATA 2] [DATA []]]]]",
        cons(data(THREE), cons(data(FOUR), data(Tuple.of(TWO, Tuple.of())))),
        Tuple.of(THREE, Tuple.of(FOUR, Tuple.of(TWO, Tuple.of())))),

    NESTED_LIST(cons(SINGLE_ELEMENT_LIST.expression, cons(data(SIX), data(Tuple
        .of()))), //
        "[CONS [CONS [DATA 2] [DATA []]] [CONS [DATA 6] [DATA []]]]",
        cons(data(Tuple.of(TWO, Tuple.of())), cons(data(SIX), data(Tuple.of()))),
        Tuple.of(Tuple.of(TWO, Tuple.of()), Tuple.of(SIX, Tuple.of()))),

    TEST_CAR(app(CAR, TRIPLE_ELEMENT_LIST.expression), //
        "[APP " + CAR_CODE + " " + TRIPLE_ELEMENT_LIST.code + "]",
        get(1, TRIPLE_ELEMENT_LIST.expression),
        THREE),

    TEST_CDR(app(CDR, TRIPLE_ELEMENT_LIST.expression), //
        "[APP " + CDR_CODE + " " + TRIPLE_ELEMENT_LIST.code + "]",
        get(2, TRIPLE_ELEMENT_LIST.expression),
        Tuple.of(FOUR, Tuple.of(TWO, Tuple.of()))),

    TEST_CADR(app(CAR, app(CDR, TRIPLE_ELEMENT_LIST.expression)), //
        "[APP " + CAR_CODE + " [APP " + CDR_CODE + " "
            + TRIPLE_ELEMENT_LIST.code + "]]",
        get(1, app(CDR, TRIPLE_ELEMENT_LIST.expression)),
        FOUR),

    TEST_CAAR(app(CAR, app(CAR, NESTED_LIST.expression)), //
        "[APP " + CAR_CODE + " [APP " + CAR_CODE + " " + NESTED_LIST.code
            + "]]",
        get(1, app(CAR, NESTED_LIST.expression)),
        TWO),

    TEST_BUILD_ADDL(app(app(ADDL, app(app(ADDL, EMPTY_LIST), data(TWO))),
        data(THREE)), //
        "[APP [APP " + ADDL_CODE + " [APP [APP " + ADDL_CODE + " "
            + EMPTY_LIST_CODE + "] [DATA 2]]] [DATA 3]]",
        null,
        Tuple.of(TWO, Tuple.of(THREE, Tuple.of()))),

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
  }

  public void test() {
    ExpressionTester.testExpressions(Arrays.asList(Tests.values()));
  }
  //
  // public void assertListContents(Tuple list, LObject... contents) {
  // for (int i = 0; i < contents.length; i++) {
  // Tuple car = Lambda.get(1, list);
  //
  // LObject obj = Lambda.reduce(car);
  // list = Lambda.get(2, list);
  //
  // assertEquals("list[" + i + "] invalid", contents[i], obj);
  // }
  //
  // assertEquals("list was longer than expected", Tuple.of(), Lambda
  // .reduce(list));
  // }
}
