package languish.lib.bootstrap.namespaces;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class NamespaceFunctions {
	public static final NativeFunction PARSE = new NativeFunction() {
		@SuppressWarnings("unchecked")
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			Tree<String> ast =
					NamespaceGrammar.GRAMMAR
							.getAstParser()
							.parse(arg.asLeaf().asString());
			Tree<String> module =
					(Tree<String>) NamespaceSemantic.LAMBDA_PLUS_SEMANTIC.process(ast);

			PrimitiveTree.fromTree(module).toString();

			return PrimitiveTree.fromTree(module);
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/parsers/namespace/native_parse", PARSE)
					.build();
}
