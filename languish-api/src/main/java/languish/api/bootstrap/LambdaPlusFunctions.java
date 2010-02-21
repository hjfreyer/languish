package languish.api.bootstrap;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class LambdaPlusFunctions {
	public static final NativeFunction GRAMMAR = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			// String text = arg.asLeaf().asString();

			return Tree.<Primitive> empty();
		}
	};

	public static final NativeFunction SEMANTIC_EVAL = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			String text = arg.asLeaf().asString();

			return Tree.<Primitive> empty();
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/parsers/lambda_plus/grammar", GRAMMAR)
					.put("builtin/parsers/lambda_plus/semantic", SEMANTIC_EVAL)
					.build();
}
