package languish.compiler;

import java.util.List;

import languish.base.Primitive;
import languish.base.Primitives;
import languish.base.Term;
import languish.base.Terms;
import languish.interpreter.Interpreter;

import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Modules {

	public static Module fromTerm(Term term) {
		Term depsListTerm = Terms.car(term);
		Term astTerm = Terms.car(Terms.cdr(term));

		List<String> deps = CompilerUtil.convertTermToStringList(depsListTerm);

		Tree<Primitive> astPrim = Interpreter.convertTermToJavaObject(astTerm);
		Tree<String> ast = Trees.transform(astPrim, Primitives.asString());

		return new Module(deps, ast);
	}

	public static Module fromParserAndDocument(Term parser, String document) {
		Term moduleTerm = Terms.app(parser, Terms.primObj(document));

		return Modules.fromTerm(moduleTerm);
	}

	private Modules() {
	}
}
