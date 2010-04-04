package languish.compiler;

import java.util.List;

import languish.base.Primitive;
import languish.base.Primitives;
import languish.base.Term;
import languish.interpreter.Interpreter;
import languish.serialization.StringTreeSerializer;

import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class CompilerUtil {

	public static Term compileTermAstToTerm(Term astTerm) {
		Tree<Primitive> astPrim = Interpreter.convertTermToJavaObject(astTerm);
		Tree<String> ast = Trees.transform(astPrim, Primitives.asString());

		return StringTreeSerializer.deserialize(ast);
	}

	public static List<String> convertTermToStringList(Term term) {
		Tree<Primitive> prims = Interpreter.convertTermToJavaObject(term);
		Tree<String> tree =
				Trees.transform(Interpreter.convertTermToJavaObject(term), //
						Primitives.asString());
		List<String> strList =
				Lists.transform(tree.asList(), Tree.<String> asLeafFunction());

		return strList;
	}
}
