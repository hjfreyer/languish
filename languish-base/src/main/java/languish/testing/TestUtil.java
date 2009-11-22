package languish.testing;

import junit.framework.TestCase;
import languish.base.Primitive;
import languish.base.Term;
import languish.interpreter.TermParser;
import languish.util.Lambda;
import languish.util.PrimitiveTree;
import languish.util.TermPrinter;

public class TestUtil {

  public static final Primitive ZERO = new Primitive(0);
  public static final Primitive ONE = new Primitive(1);
  public static final Primitive TWO = new Primitive(2);
  public static final Primitive THREE = new Primitive(3);
  public static final Primitive FOUR = new Primitive(4);
  public static final Primitive FIVE = new Primitive(5);
  public static final Primitive SIX = new Primitive(6);
  public static final Primitive SEVEN = new Primitive(7);
  public static final Primitive EIGHT = new Primitive(8);
  public static final Primitive NINE = new Primitive(9);
  public static final Primitive TEN = new Primitive(10);
  public static final Primitive ELEVEN = new Primitive(11);
  public static final Primitive TWELVE = new Primitive(12);
  public static final Primitive THIRTEEN = new Primitive(13);
  public static final Primitive FOURTEEN = new Primitive(14);
  public static final Primitive FIFTEEN = new Primitive(15);

  public static final Term IDENT = Lambda.abs(Lambda.ref(1));
  public static final Term OMEGA =
      Lambda.abs(Lambda.app(Lambda.ref(1), Lambda.ref(1)));
  public static final Term LOOP = Lambda.app(OMEGA, OMEGA);

  public static void assertLanguishTestCase(LanguishTestCase testCase) {

    String name = testCase.name();
    Term exp = testCase.getExpression();
    String code = testCase.getCode();
    Term reducedOnce = testCase.getReducedOnce();
    PrimitiveTree reducedCompletely = testCase.getReducedCompletely();

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
