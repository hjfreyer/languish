package languish.interpreter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import languish.base.Tuple;

public class FileSystemDependencyManager implements DependencyManager {

  private static final ClassLoader CLASS_LOADER =
      FileSystemDependencyManager.class.getClassLoader();

  private final List<String> paths;

  public FileSystemDependencyManager(List<String> paths) {
    this.paths = paths;
  }

  public Tuple getResource(String resourceName) throws FileNotFoundException {

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

    throw new FileNotFoundException(resourceName);
  }
}
