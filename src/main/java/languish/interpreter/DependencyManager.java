package languish.interpreter;

import java.io.FileNotFoundException;

import languish.base.Tuple;

public interface DependencyManager {
  public Tuple getResource(String resourceName) throws FileNotFoundException;
}
