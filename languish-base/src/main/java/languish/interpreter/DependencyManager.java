package languish.interpreter;

import languish.error.DependencyUnavailableError;
import languish.lambda.Term;

public interface DependencyManager {
  public Term getResource(String resourceName)
      throws DependencyUnavailableError;
}
