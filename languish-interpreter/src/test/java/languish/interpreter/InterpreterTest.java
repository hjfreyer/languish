package languish.interpreter;

import static languish.base.Terms.*;
import static org.easymock.EasyMock.*;
import junit.framework.TestCase;
import languish.base.Term;
import languish.base.Terms;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

import org.jmock.Mockery;

import com.google.common.collect.ImmutableList;

public class InterpreterTest extends TestCase {
  Mockery context = new Mockery();

  @SuppressWarnings("unchecked")
  public void testBasicValue() throws Exception {
    Term program =
        Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
            "VALUE",
            5)));

    TestUtil.assertReducesToData(PrimitiveTree.from(5), Interpreter
        .reduceModuleCompletely(program, null));
  }

  public void testLoad() throws Exception {
    Term nestedProg =
        abs(cons(primObj("VALUE"), cons(
            cons(ref(4), cons(ref(5), Term.NULL)),
            Term.NULL)));

    Term program =
        cons(primObj("LOAD"), cons(cons(primObj("depname"), cons(
            nestedProg,
            Term.NULL)), Term.NULL));

    DependencyManager mockDepMan = createMock(DependencyManager.class);

    Term dep = cons(primObj("VALUE"), cons(primObj(5), Term.NULL));
    expect(mockDepMan.getResource("depname")).andReturn(dep);
    replay(mockDepMan);

    TestUtil.assertReducesToData(
        PrimitiveTree.from(ImmutableList.of(5, 5)),
        Interpreter.reduceModuleCompletely(program, mockDepMan));

    verify(mockDepMan);
  }
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
