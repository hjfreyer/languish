package languish.lib;

import java.io.FileNotFoundException;

import languish.interpreter.DependencyManager;
import languish.interpreter.dep.FileSystemDependencyManager;
import languish.lambda.Term;
import languish.util.Lambda;

import com.google.common.collect.ImmutableList;

public class Data {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/data");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term equals() {
    return Lambda.car(LIB);
  }

}
