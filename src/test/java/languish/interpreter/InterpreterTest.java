package languish.interpreter;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.TestUtil;

public class InterpreterTest extends TestCase {

  public void testBasicDisplay() throws Exception {
    LObject res = Interpreter.interpretStatement("#lang std;; \n..", null);

    assertEqualsData(TestUtil.FIVE, res);
  }

  public void testTrivialParserChange() throws Exception {
    LObject res;

    String statementToReturn = "[CONS [DATA 0] [DATA 42]]";

    res = Interpreter.interpretStatement("SET_PARSER [ABS [ABS " //
        + statementToReturn + "]]", null);

    res = Interpreter.interpretStatement("THIS IS GARBAGE!!@E#!q34!", null);

    assertEqualsData(LInteger.of(42), res);
  }

  private void assertEqualsData(LObject expected, LObject actual) {
    assertEquals(Lambda.data(expected), actual);
  }

  public void testEchoParser() throws Exception {
    String echoCode = "[ABS [ABS [CONS [DATA 0] [REF 2]]]]";

    String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
    String test2 = "rock me am[ad]]eus!~";

    LObject res;

    res = Interpreter.interpretStatement("SET_PARSER " + echoCode, null);
    res = Interpreter.interpretStatement("", null);
    assertEqualsData(LSymbol.of(""), res);

    res = Interpreter.interpretStatement(test, null);
    assertEqualsData(LSymbol.of(test), res);

    res = Interpreter.interpretStatement(test2, null);
    assertEqualsData(LSymbol.of(test2), res);
  }

  public void testMacros() throws Exception {
    LObject res;

    res = Interpreter.interpretStatement("MACRO THREE [DATA 3]", null);

    res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
    assertEqualsData(TestUtil.THREE, res);

    res = Interpreter.interpretStatement( //
        "REDUCE [PRIM [DATA ADD] [CONS [DATA 2] (*THREE*)]]", null);
    assertEqualsData(TestUtil.FIVE, res);
  }

  public void testMacrosReplace() throws Exception {
    LObject res;

    res = Interpreter.interpretStatement("MACRO THREE [DATA 2]", null);

    res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
    assertEqualsData(TestUtil.TWO, res);

    res = Interpreter.interpretStatement("MACRO THREE [DATA 3]", null);
    res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
    assertEqualsData(TestUtil.THREE, res);

    res =
        Interpreter.interpretStatement("MACRO THREE "
            + "[ABS [PRIM [DATA ADD] [CONS [REF 1] (*THREE*)]]]", null);

    res =
        Interpreter.interpretStatement("REDUCE [APP (*THREE*) [DATA 2]]", null);
    assertEqualsData(TestUtil.FIVE, res);
  }
}
