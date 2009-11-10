package languish.interpreter;

import java.io.FileNotFoundException;

import languish.lambda.Term;

public interface DependencyManager {
  public Term getResource(String resourceName) throws FileNotFoundException;
}
