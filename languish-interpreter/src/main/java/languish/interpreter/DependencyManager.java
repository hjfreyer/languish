package languish.interpreter;

import languish.base.Term;
import languish.interpreter.error.DependencyUnavailableError;

public interface DependencyManager {
  public Term getResource(String resourceName)
      throws DependencyUnavailableError;
}