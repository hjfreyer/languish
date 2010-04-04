package languish.lib.testing;

import languish.base.Term;
import languish.compiler.DependencyManager;
import languish.compiler.Compiler;
import languish.compiler.ResourceDependencyManager;
import languish.interpreter.error.DependencyUnavailableError;
import languish.lib.LanguishLoadError;

import com.google.common.collect.ImmutableList;

public class LibTestUtil {

	public static final DependencyManager LANGUISH_FOLDER =
			new ResourceDependencyManager(ImmutableList.of("lish"));

	public static Term loadLib(String libName) {
		try {
			return Compiler.compileDependency(
					libName,
					LibTestUtil.LANGUISH_FOLDER);
		} catch (DependencyUnavailableError e) {
			throw new LanguishLoadError(e);
		}
	}
}
