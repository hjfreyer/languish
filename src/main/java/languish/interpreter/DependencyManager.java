package languish.interpreter;

import java.io.FileNotFoundException;

public interface DependencyManager {
  public String getResource(String resourceName) throws FileNotFoundException;
}
