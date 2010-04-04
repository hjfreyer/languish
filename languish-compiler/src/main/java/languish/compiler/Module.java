package languish.compiler;

import java.util.List;

import com.hjfreyer.util.Tree;

public class Module {
	private final List<String> deps;
	private final Tree<String> ast;

	public Module(List<String> deps, Tree<String> ast) {
		super();
		this.deps = deps;
		this.ast = ast;
	}

	public List<String> getDeps() {
		return deps;
	}

	public Tree<String> getAst() {
		return ast;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deps == null) ? 0 : deps.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Module other = (Module) obj;
		if (deps == null) {
			if (other.deps != null)
				return false;
		} else if (!deps.equals(other.deps))
			return false;
		return true;
	}

}
