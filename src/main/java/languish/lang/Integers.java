package languish.lang;

import java.io.FileNotFoundException;

import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;
import languish.lambda.Lambda;
import languish.lambda.Term;

import com.google.common.collect.ImmutableList;

public class Integers {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/integers");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term add() {
    return Lambda.car(LIB);
  }

  public static Term multiply() {
    return Lambda.car(Lambda.cdr(LIB));
  }

  private Integers() {
  }
}
