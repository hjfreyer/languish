package languish.primitives;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.*;
import static languish.testing.TestUtil.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

import com.hjfreyer.util.Lists;

public class ListTest extends TestCase {

  public enum Tests implements LanguishTestList {
    // IDENITY TESTS
    TEST_EMPTY_LIST(data(Tuple.of()), //
        "[DATA []]",
        null,
        Lists.of()),

    SINGLE_ELEMENT_LIST(cons(data(TWO), data(Tuple.of())), //
        "[CONS [DATA 2] [DATA []]]",
        null,
        Lists.of(TWO)),

    DOUBLE_ELEMENT_LIST(cons(data(THREE), cons(data(TWO), data(Tuple.of()))), //
        "[CONS [DATA 3] [CONS [DATA 2] [DATA []]]]",
        null,
        Lists.of(THREE, TWO)),

    TRIPLE_ELEMENT_LIST(cons(data(THREE), cons(data(FOUR), cons(data(TWO),
        data(Tuple.of())))), //
        "[CONS [DATA 3] [CONS [DATA 4] [CONS [DATA 2] [DATA []]]]]",
        null,
        Lists.of(THREE, FOUR, TWO)),

    NESTED_LIST(cons(SINGLE_ELEMENT_LIST.expression, cons(data(SIX), data(Tuple
        .of()))), //
        "[CONS [CONS [DATA 2] [DATA []]] [CONS [DATA 6] [DATA []]]]",
        null,
        Lists.of(Lists.of(TWO), SIX)),

    TEST_CAR(app(CAR, TRIPLE_ELEMENT_LIST.expression), //
        "[APP " + CAR_CODE + " " + TRIPLE_ELEMENT_LIST.code + "]",
        get(1, TRIPLE_ELEMENT_LIST.expression),
        null,
        THREE),

    TEST_CDR(app(CDR, TRIPLE_ELEMENT_LIST.expression), //
        "[APP " + CDR_CODE + " " + TRIPLE_ELEMENT_LIST.code + "]",
        get(2, TRIPLE_ELEMENT_LIST.expression),
        Lists.of(FOUR, TWO)),

    TEST_CADR(app(CAR, app(CDR, TRIPLE_ELEMENT_LIST.expression)), //
        "[APP " + CAR_CODE + " [APP " + CDR_CODE + " "
            + TRIPLE_ELEMENT_LIST.code + "]]",
        get(1, app(CDR, TRIPLE_ELEMENT_LIST.expression)),
        null,
        FOUR),

    TEST_CAAR(app(CAR, app(CAR, NESTED_LIST.expression)), //
        "[APP " + CAR_CODE + " [APP " + CAR_CODE + " " + NESTED_LIST.code
            + "]]",
        get(1, app(CAR, NESTED_LIST.expression)),
        null,
        TWO),

    TEST_BUILD_ADDL(app(app(ADDL, app(app(ADDL, EMPTY_LIST), data(TWO))),
        data(THREE)), //
        "[APP [APP " + ADDL_CODE + " [APP [APP " + ADDL_CODE + " "
            + EMPTY_LIST_CODE + "] [DATA 2]]] [DATA 3]]",
        null,
        Lists.of(TWO, THREE)),

    ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final List<?> listContents;
    private final LObject reducedCompletely;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        List<?> listContents) {
      this(expression, code, reducedOnce, listContents, null);
    }

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        List<?> listContents, LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.listContents = listContents;
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

    public List<?> getListContents() {
      return listContents;
    }

    public LObject getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
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
