package languish.interpreter;

import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.prim.data.LInteger;
import languish.prim.data.LSymbol;
import languish.testing.TestUtil;

public class InterpreterTest extends TestCase {

  private final Interpreter i = new Interpreter();

  public void testBasicDisplay() {
    LObject res = i.processStatement("REDUCE [DATA 5]");

    assertEquals(TestUtil.FIVE, res);
  }

  public void testTrivialParserChange() {
    LObject res;

    String statementToReturn = "[PAIR [DATA 0] [DATA 42]]";

    res = i.processStatement("SET_PARSER [ABS [ABS " //
        + statementToReturn + "]]");

    res = i.processStatement("THIS IS GARBAGE!!@E#!q34!");

    assertEquals(LInteger.of(42), res);
  }

  public void testEchoParser() {
    String echoCode = "[ABS [ABS [PAIR [DATA 0] [REF 2]]]]";

    String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
    String test2 = "rock me am[ad]]eus!~";

    LObject res;

    res = i.processStatement("SET_PARSER " + echoCode);
    res = i.processStatement("");
    assertEquals(LSymbol.of(""), res);

    res = i.processStatement(test);
    assertEquals(LSymbol.of(test), res);

    res = i.processStatement(test2);
    assertEquals(LSymbol.of(test2), res);
  }

  public void testMacros() {
    Object res;

    res = i.processStatement("MACRO THREE [DATA 3]");

    res = i.processStatement("REDUCE (*THREE*)");
    assertEquals(res, TestUtil.THREE);

    res = i.processStatement("REDUCE [PRIM ADD [PAIR [DATA 2] (*THREE*)]]");
    assertEquals(res, TestUtil.FIVE);
  }

  public void testMacrosReplace() {
    Object res;

    res = i.processStatement("MACRO THREE [DATA 2]");

    res = i.processStatement("REDUCE (*THREE*)");
    assertEquals(res, TestUtil.TWO);

    res = i.processStatement("MACRO THREE [DATA 3]");
    res = i.processStatement("REDUCE (*THREE*)");
    assertEquals(res, TestUtil.THREE);

    res =
        i.processStatement("MACRO THREE "
            + "[ABS [PRIM ADD [PAIR [REF 1] (*THREE*)]]]");

    res = i.processStatement("REDUCE [APP (*THREE*) [DATA 2]]");
    assertEquals(res, TestUtil.FIVE);
  }
}
