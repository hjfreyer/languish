package languish.lib.bootstrap.lambdaplus;

import java.util.List;

import junit.framework.TestCase;
import languish.base.Term;
import languish.base.Terms;
import languish.serialization.StringTreeSerializer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class LambdaPlusTest extends TestCase {

	@SuppressWarnings("unchecked")
	public static Pair<List<String>, Term> lambdaPlusParse(String document) {
		Tree<String> ast = LambdaPlusGrammar.GRAMMAR.getAstParser().parse(document);

		Tree<String> moduleStructure =
				(Tree<String>) LambdaPlusSemantic.LAMBDA_PLUS_SEMANTIC.process(ast);

		return getDepsAndTermFromModule(moduleStructure);
	}

	// TODO(hjfreyer): remove. This is a duplicate of a function in Interpreter
	public static Pair<List<String>, Term> getDepsAndTermFromModule(
			Tree<String> structure) {
		Tree<String> deps = structure.asList().get(0);
		Tree<String> termAst = structure.asList().get(1);

		List<String> depList =
				Lists.transform(deps.asList(), Tree.<String> asLeafFunction());
		Term term = StringTreeSerializer.deserialize(termAst);

		return Pair.of(depList, term);
	}

	public static void a(List<String> deps, Term term, String doc) {
		Pair<List<String>, Term> actual = lambdaPlusParse(doc);

		assertEquals(deps, actual.getFirst());
		assertEquals(term.toString(), actual.getSecond().toString());
	}

	public static void a(Term term, String doc) {
		a(ImmutableList.<String> of(), term, doc);
	}

	public void testNull() {
		a(Terms.NULL, "NULL");
	}

	public void testString() {
		a(Terms.primObj("foobar"), "\"foobar\"");
	}

	public void testCons() {
		a(Terms.fromJavaList("foo"), "[CONS \"foo\" NULL]");
	}

	public void testCons2() {
		a(Terms.fromJavaList("foo", "bar"), "[CONS \"foo\" [CONS \"bar\" NULL]]");
	}

	public void testCar() {
		a(
				Terms.car(Terms.fromJavaList("foo", "bar")),
				"[CAR [CONS \"foo\" [CONS \"bar\" NULL]]]");
	}

}
