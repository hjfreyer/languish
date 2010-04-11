package languish.lib;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class DataFunctions {
	public static final NativeFunction EQUALS = new TwoPrimitiveNativeFunction() {
		@Override
		public Tree<Primitive> apply(Primitive a, Primitive b) {
			return a.equals(b) ? PrimitiveTree.from(0) : PrimitiveTree
					.from(ImmutableList.of(0));
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/equals", EQUALS)
					.build();
}
