package languish.interpreter;

import static languish.base.Lambda.*;
import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.TestUtil;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.hjfreyer.util.Pair;

public class InterpreterTest extends TestCase {
  Mockery context = new Mockery();

  public void testGetParserAndProgram() {
    assertEquals(Pair.of("foo", "\nblah blah blah"), Interpreter
        .getParserAndProgram("#lang foo;;\nblah blah blah"));

    assertEquals(Pair.of("__BUILTIN__", "#lanr; \nblah blah blah\n\n  "),
        Interpreter.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  }

  public void testBasicDisplay() throws Exception {
    Tuple program =
        Interpreter
            .interpretStatement("[CONS [DATA \"VALUE\"] [DATA 5]]", null);

    assertReducesToData(TestUtil.FIVE, program);
  }

  public void testTrivialParserChange() throws Exception {
    final Tuple parserValue =
        abs(cons(data(LSymbol.of("VALUE")), data(LInteger.of(5))));

    final DependencyManager depman = context.mock(DependencyManager.class);
    context.checking(new Expectations() {
      {
        oneOf(depman).getResource("trivialParser");
        will(returnValue(parserValue));
      }
    });

    Tuple res =
        Interpreter.interpretStatement(
            "#lang trivialParser;; THIS IS GARBAGE!!@E#!q34!", depman);

    assertReducesToData(LInteger.of(5), res);
  }

  private void assertReducesToData(LObject expected, Tuple actual) {
    assertEquals(Lambda.data(expected), Lambda.reduce(actual));
  }

  public void testEchoParser() throws Exception {
    final Tuple parserValue = abs(cons(data(LSymbol.of("VALUE")), ref(1)));

    final DependencyManager depman = context.mock(DependencyManager.class);
    context.checking(new Expectations() {
      {
        exactly(3).of(depman).getResource("echoParser");
        will(returnValue(parserValue));
      }
    });

    String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
    String test2 = "rock me am[ad]]eus!~";

    Tuple res = Interpreter.interpretStatement("#lang echoParser;;", depman);
    assertReducesToData(LSymbol.of(""), res);

    res = Interpreter.interpretStatement("#lang echoParser;;" + test, depman);
    assertReducesToData(LSymbol.of(test), res);

    res = Interpreter.interpretStatement("#lang echoParser;;" + test2, depman);
    assertReducesToData(LSymbol.of(test2), res);
  }
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
