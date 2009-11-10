package languish.lang;

import java.io.FileNotFoundException;

import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;
import languish.lambda.Lambda;
import languish.lambda.Term;

import com.google.common.collect.ImmutableList;

public class Booleans {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/booleans");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term and() {
    return Lambda.car(LIB);
  }

  public static Term or() {
    return Lambda.car(Lambda.cdr(LIB));
  }

  public static Term not() {
    return Lambda.car(Lambda.cdr(Lambda.cdr(LIB)));
  }

  private Booleans() {
  }
}
