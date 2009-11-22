package languish.interpreter.dep;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import languish.base.Term;
import languish.error.DependencyUnavailableError;
import languish.interpreter.BaseParser;
import languish.interpreter.DependencyManager;

public class FileSystemDependencyManager implements DependencyManager {

  private static final ClassLoader CLASS_LOADER =
      FileSystemDependencyManager.class.getClassLoader();

  private final List<String> paths;

  public FileSystemDependencyManager(List<String> paths) {
    this.paths = paths;
  }

  public Term getResource(String resourceName) {

    for (String path : paths) {
      String docPath = path + '/' + resourceName + ".lish";
      InputStream stream = CLASS_LOADER.getResourceAsStream(docPath);

      if (stream == null) {
        continue;
      }

      StringBuilder doc = new StringBuilder();
      Scanner read = new Scanner(stream);

      while (read.hasNextLine()) {
        doc.append(read.nextLine()).append('\n');
      }

      return BaseParser.parseFromString(doc.toString());
    }

    throw new DependencyUnavailableError(resourceName);
  }
}