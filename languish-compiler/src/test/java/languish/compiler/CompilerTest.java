package languish.compiler;

import static languish.base.Terms.*;
import junit.framework.TestCase;
import languish.base.Term;
import languish.base.Terms;
import languish.base.testing.TestUtil;
import languish.compiler.testing.MapDependencyManager;
import languish.compiler.testing.PartiallyMockedCompiler;
import languish.serialization.StringTreeSerializer;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class CompilerTest extends TestCase {

	public Compiler compile;
	public DependencyManager depman;

	public void testSimpleModule() {
		compile = new Compiler(null);

		Module simple = new Module(ImmutableList.<String> of(), //
				StringTreeSerializer.serialize(Terms.abs(Terms.primObj("foobar"))));

		TestUtil.assertReducesToData(PrimitiveTree.from("foobar"), compile
				.compileModule(simple));
	}

	public void testSimpleModuleStringForm() {
		String program =
				"[ \"ABS\" [\"PRIMITIVE\" \"sFooBar\" \"\"] "
						+ "[\"PRIMITIVE\" \"i0\" \"\"]]";

		depman = new MapDependencyManager(ImmutableMap.of("simple", program));
		compile = new Compiler(depman);

		TestUtil.assertReducesToData(PrimitiveTree.from("FooBar"), compile
				.compileResource("simple"));
	}

	public void testStupidCustomParser() {
		// program that returns just bulbasaur
		Tree<String> ast =
				StringTreeSerializer.serialize(abs(primObj("bulbasaur")));
		Term astTerm = Terms.fromPrimitiveTree(PrimitiveTree.fromTree(ast));

		Term parser = abs(cons(NULL, cons(astTerm, NULL)));
		String testDoc = "#lang stupid_parser;; check me out";

		depman = new MapDependencyManager(ImmutableMap.of("test_doc", testDoc));
		compile =
				new PartiallyMockedCompiler(depman, ImmutableMap.of(
						"stupid_parser",
						parser));

		TestUtil.assertReducesToData(PrimitiveTree.from("bulbasaur"), compile
				.compileResource("test_doc"));
	}
	//
	// public void testGetDepParser() throws Exception {
	// NativeFunction func = new NativeFunction() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public Tree<Primitive> apply(Tree<Primitive> arg) {
	// List<String> primTerm = ImmutableList.of("PRIMITIVE", arg.asLeaf()
	// .asString(), "NULL");
	// List<?> funcWrapTerm = ImmutableList.of("ABS", primTerm, "NULL");
	// return PrimitiveTree.from(ImmutableList.of(ImmutableList.of(),
	// funcWrapTerm));
	// }
	// };
	// final DependencyManager depman = mocker.mock(DependencyManager.class);
	// mocker.checking(new Expectations() {
	// {
	// oneOf(depman).getResource("test/dep_get_parser");
	// will(Expectations.returnValue("#nativefunction:test_echo_parser"));
	// }
	// });
	// Map<String, NativeFunction> funcMap = ImmutableMap
	// .<String, NativeFunction> of("test_echo_parser", func);
	// String doc = "#lang test/echo_parser;;  foobar\nbaz";
	// Term interpreted = Interpreter.interpret(doc, depman, funcMap);
	// TestUtil.assertReducesToData(PrimitiveTree.from("  foobar\nbaz"),
	// interpreted);
	// }
	//
	// public void testEchoParser() throws Exception {
	// Term identAst = StringTreeSerializer.deser(abs(ref(1)));
	// // Term echoParser = abs(cons("APP"
	//
	// expect(depman.getResource("test/echo_parser")).andReturn(
	// BinarySerializer.serializeModule(new Module(
	// ImmutableList.<String> of(),
	// echoParser)));
	// replay(depman);
	//
	// String doc = "#lang test/echo_parser;;  foobar\nbaz";
	// Term interpreted = Compiler.compile(doc, depman);
	//
	// // TestUtil.assertReducesToData(
	// // PrimitiveTree.from("  foobar\nbaz"),
	// // interpreted);
	// }
	// public void testParseNonstandardParser() throws Exception {
	// Term res = BaseParser //
	// .parseFromString("#lang fooParser;; blah blah blah");
	// Term depName = primitive(new Primitive("fooParser"));
	// Term programApplication = app(ref(3), primitive(new Primitive(
	// " blah blah blah")));
	// Term expected = cons(primitive(new Primitive("LOAD")), //
	// cons(cons(depName, cons(programApplication, Term.NULL)), Term.NULL));
	// Assert.assertEquals(expected, res);
	// }

	// public void testParseBuiltinParser() throws Exception {
	// Term res = BaseParser.parseFromString("#lang __BUILTIN__;; "
	// + "[PRIMITIVE \"Returned as-is\" NULL]");
	// assertEquals(Tree.leaf(new Primitive("Returned as-is")), Terms
	// .convertTermToJavaObject(res));
	// }
	//
	// public void testParseNonstandardParser() throws Exception {
	// Term res = BaseParser //
	// .parseFromString("#lang fooParser;; blah blah blah");
	// Term depName = Terms.primitive(new Primitive("fooParser"));
	// Term programApplication = Terms.app(Terms.ref(3), Terms
	// .primitive(new Primitive(" blah blah blah")));
	// Term expected = Terms.cons(Terms.primitive(new Primitive("LOAD")), //
	// Terms.cons(Terms.cons(depName, Terms
	// .cons(programApplication, Term.NULL)), Term.NULL));
	// assertEquals(expected, res);
	// }

	// Mockery context = new Mockery();
	//
	// @SuppressWarnings("unchecked")
	// public void testBasicValue() throws Exception {
	// Term program =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "VALUE",
	// 5)));
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(5), Interpreter
	// .reduceModuleCompletely(program, null));
	// }
	//
	// public void testLoad() throws Exception {
	// Term nestedProg =
	// abs(cons(primObj("VALUE"), cons(
	// cons(ref(4), cons(ref(5), Term.NULL)),
	// Term.NULL)));
	//
	// Term program =
	// cons(primObj("LOAD"), cons(cons(primObj("depname"), cons(
	// nestedProg,
	// Term.NULL)), Term.NULL));
	//
	// DependencyManager mockDepMan = createMock(DependencyManager.class);
	//
	// Term dep = cons(primObj("VALUE"), cons(primObj(5), Term.NULL));
	// expect(mockDepMan.getResource("depname")).andReturn(dep);
	// replay(mockDepMan);
	//
	// TestUtil.assertReducesToData(
	// PrimitiveTree.from(ImmutableList.of(5, 5)),
	// Interpreter.reduceModuleCompletely(program, mockDepMan));
	//
	// verify(mockDepMan);
	// }
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
