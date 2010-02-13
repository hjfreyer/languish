package languish.interpreter;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import languish.interpreter.error.DependencyUnavailableError;

public class FileSystemDependencyManager implements DependencyManager {

  private static final ClassLoader CLASS_LOADER = FileSystemDependencyManager.class
      .getClassLoader();
  private final List<String> paths;

  public FileSystemDependencyManager(List<String> paths) {
    this.paths = paths;
  }

  /* (non-Javadoc)
   * @see languish.interpreter.fooDependencyManager#getResource(java.lang.String)
   */
  public String getResource(String resourceName) {
    for (String path : paths) {
      String docPath = path + '/' + resourceName + ".lish";
      InputStream stream = FileSystemDependencyManager.CLASS_LOADER
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

  /* (non-Javadoc)
   * @see languish.interpreter.fooDependencyManager#hasResource(java.lang.String)
   */
  public boolean hasResource(String resourceName) {
    for (String path : paths) {
      String docPath = path + '/' + resourceName + ".lish";
      if (FileSystemDependencyManager.CLASS_LOADER.getResource(docPath) != null) {
        return true;
      }
    }
    return false;
  }

}
