package languish.testing;

import junit.framework.TestCase;
import languish.interpreter.TermParser;
import languish.lambda.Term;
import languish.primitives.LInteger;
import languish.util.JavaWrapper;
import languish.util.Lambda;
import languish.util.TermPrinter;

public class TestUtil {

  public static final LInteger ZERO = LInteger.of(0);
  public static final LInteger ONE = LInteger.of(1);
  public static final LInteger TWO = LInteger.of(2);
  public static final LInteger THREE = LInteger.of(3);
  public static final LInteger FOUR = LInteger.of(4);
  public static final LInteger FIVE = LInteger.of(5);
  public static final LInteger SIX = LInteger.of(6);
  public static final LInteger SEVEN = LInteger.of(7);
  public static final LInteger EIGHT = LInteger.of(8);
  public static final LInteger NINE = LInteger.of(9);
  public static final LInteger TEN = LInteger.of(10);
  public static final LInteger ELEVEN = LInteger.of(11);
  public static final LInteger TWELVE = LInteger.of(12);
  public static final LInteger THIRTEEN = LInteger.of(13);
  public static final LInteger FOURTEEN = LInteger.of(14);
  public static final LInteger FIFTEEN = LInteger.of(15);

  public static final Term IDENT = Lambda.abs(Lambda.ref(1));

  public static void assertLanguishTestCase(LanguishTestCase testCase) {

    String name = testCase.name();
    Term exp = testCase.getExpression();
    String code = testCase.getCode();
    Term reducedOnce = testCase.getReducedOnce();
    JavaWrapper reducedCompletely = testCase.getReducedCompletely();

    if (code != null) {
      // TOSTRING
      TestCase.assertEquals("on test " + name
          + " - getCodeForExpression() does not match code:", code, TermPrinter
          .getCodeForTerm(exp));

      // PARSE
      Term parsed = TermParser.TERM.parse(code);

      TestCase.assertEquals("on test " + name
          + " - code does not parse to given expression:", exp, parsed);
    }
    // REDUCE ONCE
    if (reducedOnce != null) {
      TestCase.assertEquals("on test " + name
          + " - expression does not reduce once to given value:", reducedOnce,
          exp.reduce());
    }

    // REDUCE COMPLETELY
    if (reducedCompletely != null) {
      TestCase.assertEquals("on test " + name
          + " - expression does not ultimately reduce to given value:",
          reducedCompletely, Lambda.convertTermToJavaObject(exp));
    }
  }
}
