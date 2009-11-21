package languish.interpreter.dep;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import languish.error.DependencyUnavailableError;
import languish.interpreter.DependencyManager;
import languish.interpreter.Interpreter;
import languish.lambda.Term;

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

      return Interpreter.interpretStatement(doc.toString(), this);
    }

    throw new DependencyUnavailableError(resourceName);
  }
}
