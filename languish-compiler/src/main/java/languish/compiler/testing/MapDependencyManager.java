package languish.compiler.testing;

import java.util.Map;

import languish.compiler.DependencyManager;

public class MapDependencyManager implements DependencyManager {

	private final Map<String, String> data;

	public MapDependencyManager(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String getResource(String resourceName) {
		return data.get(resourceName);
	}

	@Override
	public boolean hasResource(String resourceName) {
		return data.containsKey(resourceName);
	}
}
