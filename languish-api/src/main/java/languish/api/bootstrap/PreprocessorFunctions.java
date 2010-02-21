package languish.api.bootstrap;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class PreprocessorFunctions {

	public static final NativeFunction PREPROC_FUNC = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			String text = arg.asLeaf().asString();

			return Tree.<Primitive> empty();
			// return Preprocessor.process(text);
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder().put(
					"bootstrap/preprocessor",
					PREPROC_FUNC).build();

}
