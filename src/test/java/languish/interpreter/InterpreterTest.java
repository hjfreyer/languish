package languish.interpreter;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.testing.TestUtil;

import com.hjfreyer.util.Pair;

public class InterpreterTest extends TestCase {

  public void testGetParserAndProgram() {
    assertEquals(Pair.of("foo", "\nblah blah blah"), Interpreter
        .getParserAndProgram("#lang foo;;\nblah blah blah"));

    assertEquals(Pair.of("__BUILTIN__", "#lanr; \nblah blah blah\n\n  "),
        Interpreter.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  }

  public void testBasicDisplay() throws Exception {
    Tuple program = Interpreter.interpretStatement("[ABS [DATA 5]]", null);

    assertReducesToData(TestUtil.FIVE, program);
  }

  public void testTrivialParserChange() throws Exception {
    LObject res;

    String statementToReturn = "[CONS [DATA 0] [DATA 42]]";

    res = Interpreter.interpretStatement("SET_PARSER [ABS [ABS " //
        + statementToReturn + "]]", null);

    res = Interpreter.interpretStatement("THIS IS GARBAGE!!@E#!q34!", null);

    // assertReducesToData(LInteger.of(42), res);
  }

  private void assertReducesToData(LObject expected, Tuple actual) {
    assertEquals(Lambda.data(expected), Lambda.reduce(actual));
  }
  //
  // public void testEchoParser() throws Exception {
  // String echoCode = "[ABS [ABS [CONS [DATA 0] [REF 2]]]]";
  //
  // String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
  // String test2 = "rock me am[ad]]eus!~";
  //
  // LObject res;
  //
  // res = Interpreter.interpretStatement("SET_PARSER " + echoCode, null);
  // res = Interpreter.interpretStatement("", null);
  // // assertReducesToData(LSymbol.of(""), res);
  //
  // res = Interpreter.interpretStatement(test, null);
  // assertReducesToData(LSymbol.of(test), res);
  //
  // res = Interpreter.interpretStatement(test2, null);
  // assertReducesToData(LSymbol.of(test2), res);
  // }
  //
  // public void testMacros() throws Exception {
  // LObject res;
  //
  // res = Interpreter.interpretStatement("MACRO THREE [DATA 3]", null);
  //
  // res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
  // assertReducesToData(TestUtil.THREE, res);
  //
  // res = Interpreter.interpretStatement( //
  // "REDUCE [PRIM [DATA ADD] [CONS [DATA 2] (*THREE*)]]", null);
  // assertReducesToData(TestUtil.FIVE, res);
  // }
  //
  // public void testMacrosReplace() throws Exception {
  // LObject res;
  //
  // res = Interpreter.interpretStatement("MACRO THREE [DATA 2]", null);
  //
  // res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
  // assertReducesToData(TestUtil.TWO, res);
  //
  // res = Interpreter.interpretStatement("MACRO THREE [DATA 3]", null);
  // res = Interpreter.interpretStatement("REDUCE (*THREE*)", null);
  // assertReducesToData(TestUtil.THREE, res);
  //
  // res =
  // Interpreter.interpretStatement("MACRO THREE "
  // + "[ABS [PRIM [DATA ADD] [CONS [REF 1] (*THREE*)]]]", null);
  //
  // res =
  // Interpreter.interpretStatement("REDUCE [APP (*THREE*) [DATA 2]]", null);
  // assertReducesToData(TestUtil.FIVE, res);
  // }
}
