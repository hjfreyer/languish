package languish.interpreter;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.TestUtil;

public class InterpreterTest extends TestCase {

  private final Interpreter i = new Interpreter();

  public void testBasicDisplay() {
    LObject res = i.processStatement("REDUCE [DATA 5]");

    assertEqualsData(TestUtil.FIVE, res);
  }

  public void testTrivialParserChange() {
    LObject res;

    String statementToReturn = "[CONS [DATA 0] [DATA 42]]";

    res = i.processStatement("SET_PARSER [ABS [ABS " //
        + statementToReturn + "]]");

    res = i.processStatement("THIS IS GARBAGE!!@E#!q34!");

    assertEqualsData(LInteger.of(42), res);
  }

  private void assertEqualsData(LObject expected, LObject actual) {
    assertEquals(Lambda.data(expected), actual);
  }

  public void testEchoParser() {
    String echoCode = "[ABS [ABS [CONS [DATA 0] [REF 2]]]]";

    String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
    String test2 = "rock me am[ad]]eus!~";

    LObject res;

    res = i.processStatement("SET_PARSER " + echoCode);
    res = i.processStatement("");
    assertEqualsData(LSymbol.of(""), res);

    res = i.processStatement(test);
    assertEqualsData(LSymbol.of(test), res);

    res = i.processStatement(test2);
    assertEqualsData(LSymbol.of(test2), res);
  }

  public void testMacros() {
    LObject res;

    res = i.processStatement("MACRO THREE [DATA 3]");

    res = i.processStatement("REDUCE (*THREE*)");
    assertEqualsData(TestUtil.THREE, res);

    res = i.processStatement( //
        "REDUCE [PRIM [DATA ADD] [CONS [DATA 2] (*THREE*)]]");
    assertEqualsData(TestUtil.FIVE, res);
  }

  public void testMacrosReplace() {
    LObject res;

    res = i.processStatement("MACRO THREE [DATA 2]");

    res = i.processStatement("REDUCE (*THREE*)");
    assertEqualsData(TestUtil.TWO, res);

    res = i.processStatement("MACRO THREE [DATA 3]");
    res = i.processStatement("REDUCE (*THREE*)");
    assertEqualsData(TestUtil.THREE, res);

    res =
        i.processStatement("MACRO THREE "
            + "[ABS [PRIM [DATA ADD] [CONS [REF 1] (*THREE*)]]]");

    res = i.processStatement("REDUCE [APP (*THREE*) [DATA 2]]");
    assertEqualsData(TestUtil.FIVE, res);
  }
}
