package languish.depman;

import java.util.List;

import languish.base.Term;
import languish.interpreter.DependencyManager;
import languish.interpreter.error.DependencyUnavailableError;

public class ChainedDependencyManager implements DependencyManager {

  private final List<DependencyManager> dependencyManagers;

  public ChainedDependencyManager(List<DependencyManager> dependencyManagers) {
    this.dependencyManagers = dependencyManagers;
  }

  @Override
  public Term getResource(String resourceName)
      throws DependencyUnavailableError {
    for (DependencyManager depman : dependencyManagers) {
      if (depman.hasResource(resourceName)) {
        return depman.getResource(resourceName);
      }
    }
    throw new DependencyUnavailableError(resourceName);
  }

  @Override
  public boolean hasResource(String resourceName) {
    for (DependencyManager depman : dependencyManagers) {
      if (depman.hasResource(resourceName)) {
        return true;
      }
    }
    return false;
  }

}
