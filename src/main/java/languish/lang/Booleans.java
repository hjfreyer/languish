package languish.lang;

import java.io.FileNotFoundException;

import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;

import com.google.common.collect.ImmutableList;

public class Booleans {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Tuple LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/booleans");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Tuple and() {
    return Lambda.car(LIB);
  }

  public static Tuple or() {
    return Lambda.car(Lambda.cdr(LIB));
  }

  public static Tuple not() {
    return Lambda.car(Lambda.cdr(Lambda.cdr(LIB)));
  }

  private Booleans() {
  }
}
