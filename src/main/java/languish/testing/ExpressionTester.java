package languish.testing;

import java.util.List;

import junit.framework.TestCase;
import languish.interpreter.BuiltinParser;
import languish.lambda.Canonizer;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;

public class ExpressionTester {

  private static final BuiltinParser parser = new BuiltinParser();

  public static void testExpressions(
      List<? extends ExpressionToTest> expressions) {

    for (ExpressionToTest expToTest : expressions) {
      Tuple exp = expToTest.getExpression();
      String code = expToTest.getCode();
      Tuple reducedOnce = expToTest.getReducedOnce();
      LObject reducedCompletely = expToTest.getReducedCompletely();
      //
      // Expression gen = Canonizer.getGeneratingExpressionFor(exp);
      //
      // TestCase.assertEquals("on test " + expToTest.name()
      // + " - expression's generator does not "
      // + "ultimately reduce to expression:", exp, Reducer.reduce(gen));

      if (code != null) {
        // TOSTRING
        TestCase.assertEquals("on test " + expToTest.name()
            + " - getCodeForExpression() does not match code:", code, Canonizer
            .getCodeForExpression(exp));

        // PARSE
        LObject parsed =
            parser.parseStatementToExpression("REDUCE " + code).getSecond();

        TestCase.assertEquals("on test " + expToTest.name()
            + " - code does not parse to given expression:", parsed, exp);
      }
      // REDUCE ONCE
      if (reducedOnce != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not reduce once to given value:",
            reducedOnce, Lambda.reduceTupleOnce(exp.deepClone()));
      }

      // REDUCE COMPLETELY
      if (reducedCompletely != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not ultimately reduce to given value:",
            reducedCompletely, Lambda.reduce(exp));
      }
    }
  }
}
