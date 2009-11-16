package languish.lang;

import java.io.FileNotFoundException;

import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;
import languish.lambda.Term;
import languish.util.Lambda;

import com.google.common.collect.ImmutableList;

public class Visitor {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/visitor");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term visitTree() {
    return Lambda.car(LIB);
  }

  private Visitor() {
  }
}
