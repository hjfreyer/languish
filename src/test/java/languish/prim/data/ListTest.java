package languish.prim.data;

import static languish.lambda.Lambda.*;
import static languish.testing.TestConstants.*;

import java.util.Arrays;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Tuple;
import languish.testing.ExpressionTester;
import languish.testing.ExpressionToTest;

public class ListTest extends TestCase {

  public static final Tuple CAR = abs(get(1, ref(1)));
  public static final String CAR_CODE = "[ABS [GET 1 [REF 1]]]";

  public static final Tuple CDR = abs(get(2, ref(1)));
  public static final String CDR_CODE = "[ABS [GET 2 [REF 1]]]";

  public enum Tests implements ExpressionToTest {
    // IDENITY TESTS
    EMPTY_LIST(data(Tuple.of()), //
        "[DATA []]",
        null,
        Tuple.of()),

    SINGLE_ELEMENT_LIST(pair(data(TWO), data(Tuple.of())), //
        "[PAIR [DATA 2] [DATA []]]",
        data(Tuple.of(TWO, Tuple.of())),
        Tuple.of(TWO, Tuple.of())),

    DOUBLE_ELEMENT_LIST(pair(data(THREE), pair(data(TWO), data(Tuple.of()))), //
        "[PAIR [DATA 3] [PAIR [DATA 2] [DATA []]]]",
        pair(data(THREE), data(Tuple.of(TWO, Tuple.of()))),
        Tuple.of(THREE, Tuple.of(TWO, Tuple.of()))),

    TRIPLE_ELEMENT_LIST(pair(data(THREE), pair(data(FOUR), pair(data(TWO),
        data(Tuple.of())))), //
        "[PAIR [DATA 3] [PAIR [DATA 4] [PAIR [DATA 2] [DATA []]]]]",
        pair(data(THREE), pair(data(FOUR), data(Tuple.of(TWO, Tuple.of())))),
        Tuple.of(THREE, Tuple.of(FOUR, Tuple.of(TWO, Tuple.of())))),

    NESTED_LIST(pair(SINGLE_ELEMENT_LIST.expression, pair(data(SIX), data(Tuple
        .of()))), //
        "[PAIR [PAIR [DATA 2] [DATA []]] [PAIR [DATA 6] [DATA []]]]",
        pair(data(Tuple.of(TWO, Tuple.of())), pair(data(SIX), data(Tuple.of()))),
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