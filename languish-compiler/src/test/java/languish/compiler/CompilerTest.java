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
		// program that returns just "bulbasaur"
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

	public void testGetDepedency() {
		// program that returns the value of its second dependency
		Tree<String> ast = StringTreeSerializer.serialize(abs(car(cdr(ref(1)))));
		Module module = new Module(ImmutableList.of("dep_a", "dep_b"), ast);

		compile = new PartiallyMockedCompiler(null, ImmutableMap.of( //
				"dep_a",
				Terms.primObj("foo"),
				"dep_b",
				Terms.primObj("bar")));

		TestUtil.assertReducesToData(PrimitiveTree.from("bar"), compile
				.compileModule(module));
	}

	public void testUpToEscapingEchoParser() {
		Term nullAst =
				Terms.fromPrimitiveTree(PrimitiveTree.fromTree(StringTreeSerializer
						.serialize(Terms.NULL)));

		Term primAst =
				cons(primObj("PRIMITIVE"), cons(ref(1), cons(nullAst, NULL)));
		Term absAst = cons(primObj("ABS"), cons(primAst, cons(nullAst, NULL)));
		Term parser = abs(cons(NULL, cons(absAst, NULL)));

		String testDoc = "#lang echo_parser;;sSome string";

		depman = new MapDependencyManager(ImmutableMap.of("test_doc", testDoc));
		compile = new PartiallyMockedCompiler(depman, ImmutableMap.of( //
				"echo_parser",
				parser));

		TestUtil.assertReducesToData(PrimitiveTree.from("Some string"), compile
				.compileResource("test_doc"));
	}

}
