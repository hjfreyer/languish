package languish.interpreter;

import junit.framework.TestCase;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Canonizer;
import languish.lambda.Expression;
import languish.lambda.Reference;
import languish.lambda.Wrapper;
import languish.prim.data.LComposites;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;
import languish.prim.data.LSymbol;
import languish.testing.TestConstants;

public class InterpreterTest extends TestCase {

  private final Interpreter i = new Interpreter();

  public void testBasicDisplay() {
    LObject res = i.processStatement("DISP (!5!)");

    assertEquals(res, TestConstants.FIVE);
  }

  public void testTrivialParserChange() {
    LObject res;

    String statementToReturn = "(APP (APP (APP (~WRAP~) (!2!)) (!0!)) (!42!))";

    res =
        i.processStatement("SET_PARSER (APP (~MK_ABS~) (APP (~MK_ABS~) "
            + "(APP (~MK_WRAPPER~) " + statementToReturn + ")))");

    res = i.processStatement("THIS IS GARBAGE!!@E#!q34!");

    assertEquals(LInteger.of(42), res);
  }

  public void testEchoParser() {
    Expression result =
        Application.of(Application.of(Application.of(LComposites.WRAP, Wrapper
            .of(LInteger.of(2))), Wrapper.of(LInteger.of(0))), Reference.to(2));

    Expression echo = Abstraction.of(Abstraction.of(result));
    Expression genExp = Canonizer.getGeneratingExpressionFor(echo);

    String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
    String test2 = "rock me amadeus!~";

    LObject res;

    res = i.processStatement("SET_PARSER " + genExp);
    res = i.processStatement("");
    assertEquals(LSymbol.of(""), res);

    res = i.processStatement(test);
    assertEquals(LSymbol.of(test), res);

    res = i.processStatement(test2);
    assertEquals(LSymbol.of(test2), res);
  }
}
