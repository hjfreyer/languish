package languish.interpreter;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import languish.interpreter.error.DependencyUnavailableError;

public class DependencyManager {

  private static final ClassLoader CLASS_LOADER = DependencyManager.class
      .getClassLoader();
  private final List<String> paths;

  public DependencyManager(List<String> paths) {
    this.paths = paths;
  }

  public String getResource(String resourceName) {
    for (String path : paths) {
      String docPath = path + '/' + resourceName + ".lish";
      InputStream stream = DependencyManager.CLASS_LOADER
          .getResourceAsStream(docPath);
      if (stream == null) {
        continue;
      }
      StringBuilder doc = new StringBuilder();
      Scanner read = new Scanner(stream);
      while (read.hasNextLine()) {
        doc.append(read.nextLine()).append('\n');
      }

      return doc.toString();
    }
    throw new DependencyUnavailableError(resourceName);
  }

  public boolean hasResource(String resourceName) {
    for (String path : paths) {
      String docPath = path + '/' + resourceName + ".lish";
      if (DependencyManager.CLASS_LOADER.getResource(docPath) != null) {
        return true;
      }
    }
    return false;
  }

}
