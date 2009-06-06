package languish.testing;

import java.util.List;

import junit.framework.TestCase;
import languish.interpreter.BuiltinParser;
import languish.lambda.Expression;
import languish.prim.data.LObject;

public class TestExpressions {

  private static final BuiltinParser parser = new BuiltinParser();

  public static void testExpressions(
      List<? extends ExpressionToTest> expressions) {

    for (ExpressionToTest expToTest : expressions) {
      Expression exp = expToTest.getExpression();
      String code = expToTest.getCode();
      Expression reducedOnce = expToTest.getReducedOnce();
      LObject reducedCompletely = expToTest.getReducedCompletely();

      // TOSTRING
      TestCase.assertEquals("on test " + expToTest.name()
          + " - toString() does not match code:", code, exp.toString());

      // PARSE
      Expression parsed =
          parser.parseStatementToExpression("DISP " + code).getSecond();

      TestCase.assertEquals("on test " + expToTest.name()
          + " - code does not parse to given expression:", parsed, exp);

      // REDUCE ONCE
      if (reducedOnce != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not reduce once to given value:",
            reducedOnce, exp.reduceOnce());
      }

      // REDUCE COMPLETELY
      if (reducedCompletely != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not ultimately reduce to given value:",
            reducedCompletely, exp.reduceCompletely());
      }
    }
  }
}
