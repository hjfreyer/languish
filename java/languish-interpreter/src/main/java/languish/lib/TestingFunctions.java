package languish.lib;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import languish.base.NativeFunction;
import languish.base.Primitive;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class TestingFunctions {

	public static final NativeFunction RUN_TESTS = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			List<Tree<Primitive>> testCases = arg.asList();

			for (Tree<Primitive> testCase : testCases) {
				String name = testCase.asList().get(0).asLeaf().asString();
				Tree<Primitive> expected = testCase.asList().get(1);
				Tree<Primitive> actual = testCase.asList().get(2);
				try {
					TestCase.assertEquals(name, expected, actual);
				} catch (Exception e) {
					throw new RuntimeException("Failure in test " + name, e);
				}
			}
			return Tree.empty();
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/testing/test_runner", RUN_TESTS)
					.build();
}
