package languish.lang;

import java.io.FileNotFoundException;

import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;

import com.google.common.collect.ImmutableList;

public class Data {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Tuple LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/data");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Tuple equals() {
    return Lambda.car(LIB);
  }

}
