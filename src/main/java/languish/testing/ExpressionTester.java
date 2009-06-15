package languish.testing;

import java.util.List;

import junit.framework.TestCase;
import languish.interpreter.BuiltinParser;
import languish.lambda.Canonizer;
import languish.lambda.Expression;
import languish.lambda.Reducer;
import languish.prim.data.LObject;

public class ExpressionTester {

  private static final BuiltinParser parser = new BuiltinParser();

  public static void testExpressions(
      List<? extends ExpressionToTest> expressions) {

    for (ExpressionToTest expToTest : expressions) {
      Expression exp = expToTest.getExpression();
      String code = expToTest.getCode();
      Expression reducedOnce = expToTest.getReducedOnce();
      LObject reducedCompletely = expToTest.getReducedCompletely();

      Expression gen = Canonizer.getGeneratingExpressionFor(exp);

      TestCase.assertEquals("on test " + expToTest.name()
          + " - expression's generator does not "
          + "ultimately reduce to expression:", exp, Reducer.reduce(gen));

      if (code != null) {
        // TOSTRING
        TestCase.assertEquals("on test " + expToTest.name()
            + " - getCodeForExpression() does not match code:", code, Canonizer
            .getCodeForExpression(exp));

        // PARSE
        Expression parsed =
            parser.parseStatementToExpression("DISP " + code).getSecond();

        TestCase.assertEquals("on test " + expToTest.name()
            + " - code does not parse to given expression:", parsed, exp);
      }
      // REDUCE ONCE
      if (reducedOnce != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not reduce once to given value:",
            reducedOnce, Reducer.reduceOnce(exp));
      }

      // REDUCE COMPLETELY
      if (reducedCompletely != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not ultimately reduce to given value:",
            reducedCompletely, Reducer.reduce(exp));
      }
    }
  }
}
