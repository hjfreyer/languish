package languish.lib.testing;

import languish.base.Term;
import languish.compiler.Compiler;
import languish.compiler.DependencyManager;
import languish.compiler.ResourceDependencyManager;

import com.google.common.collect.ImmutableList;

public class LibTestUtil {

	public static final DependencyManager LANGUISH_FOLDER =
			new ResourceDependencyManager(ImmutableList.of("lish"));

	public static final Compiler TEST_COMPILER = new Compiler(LANGUISH_FOLDER);

	public static Term loadLib(String libName) {
		return TEST_COMPILER.compileResource(libName);
	}
}
