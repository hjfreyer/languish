package languish.interpreter;

import junit.framework.TestCase;

import org.jmock.Mockery;

public class InterpreterTest extends TestCase {
  Mockery context = new Mockery();

  public void testFoo() {
  }
  //
  // public void testGetParserAndProgram() {
  // assertEquals(Pair.of("foo", "\nblah blah blah"), Interpreter
  // .getParserAndProgram("#lang foo;;\nblah blah blah"));
  //
  // assertEquals(Pair.of("__BUILTIN__", "#lanr; \nblah blah blah\n\n  "),
  // Interpreter.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  // }
  //
  // //
  // // public void testBasicDisplay() throws Exception {
  // // Term program =
  // // Interpreter
  // // .interpretStatement("[CONS [DATA \"VALUE\"] [DATA 5]]", null);
  // //
  // // assertEquals(JavaWrapper.of(5),
  // Lambda.convertTermToJavaObject(program));
  // // }
  //
  // public void testTrivialParserChange() throws Exception {
  // final Term parserValue =
  // abs(cons(primitive(LSymbol.of("VALUE")), primitive(LInteger.of(5))));
  //
  // final DependencyManager depman = context.mock(DependencyManager.class);
  // context.checking(new Expectations() {
  // {
  // oneOf(depman).getResource("trivialParser");
  // will(returnValue(parserValue));
  // }
  // });
  //
  // Term res =
  // Interpreter.interpretStatement(
  // "#lang trivialParser;; THIS IS GARBAGE!!@E#!q34!", depman);
  //
  // assertEquals(JavaWrapper.of(5), Lambda.convertTermToJavaObject(res));
  // }
  //
  // public void testEchoParser() throws Exception {
  // final Term parserValue = abs(cons(primitive(LSymbol.of("VALUE")), ref(1)));
  //
  // final DependencyManager depman = context.mock(DependencyManager.class);
  // context.checking(new Expectations() {
  // {
  // exactly(3).of(depman).getResource("echoParser");
  // will(returnValue(parserValue));
  // }
  // });
  //
  // String test = "SDKFJLSKDJFLKSDF<kndslfksldf";
  // String test2 = "rock me am[ad]]eus!~";
  //
  // Term res = Interpreter.interpretStatement("#lang echoParser;;", depman);
  // assertEquals(JavaWrapper.of(""), Lambda.convertTermToJavaObject(res));
  //
  // res = Interpreter.interpretStatement("#lang echoParser;;" + test, depman);
  // assertEquals(JavaWrapper.of(test), Lambda.convertTermToJavaObject(res));
  //
  // res = Interpreter.interpretStatement("#lang echoParser;;" + test2, depman);
  // assertEquals(JavaWrapper.of(test2), Lambda.convertTermToJavaObject(res));
  // }
}