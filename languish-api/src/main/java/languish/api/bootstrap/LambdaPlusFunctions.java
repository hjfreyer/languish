package languish.api.bootstrap;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.base.Primitives;
import languish.parsing.GrammarModules;
import languish.tools.parsing.LambdaPlusGrammar;
import languish.tools.parsing.LambdaPlusSemantic;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class LambdaPlusFunctions {
	public static final NativeFunction GRAMMAR = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			Tree<String> moduleTree =
					GrammarModules.moduleToStringTree(LambdaPlusGrammar.GRAMMAR);
			return Primitives.treeToPrimitiveTree(moduleTree);
		}
	};

	public static final NativeFunction SEMANTIC_EVAL = new NativeFunction() {
		@SuppressWarnings("unchecked")
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			Tree<String> ast = Trees.transform(arg, Primitives.asString());

			return Primitives
					.treeToPrimitiveTree((Tree<String>) LambdaPlusSemantic.LAMBDA_PLUS_SEMANTIC
							.process(ast));
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/parsers/lambda_plus/grammar", GRAMMAR)
					.put("builtin/parsers/lambda_plus/semantic", SEMANTIC_EVAL)
					.build();
}
