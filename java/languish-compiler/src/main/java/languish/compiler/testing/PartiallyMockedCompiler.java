package languish.compiler.testing;

import java.util.Map;

import languish.base.Term;
import languish.compiler.Compiler;
import languish.compiler.DependencyManager;

public class PartiallyMockedCompiler extends Compiler {

	private final Map<String, Term> mocks;

	public PartiallyMockedCompiler(
			DependencyManager dependencyManager,
			Map<String, Term> mocks) {
		super(dependencyManager);
		this.mocks = mocks;
	}

	@Override
	public Term compileResource(String resourceName) {
		return mocks.containsKey(resourceName) ? mocks.get(resourceName) : super
				.compileResource(resourceName);
	}
}
