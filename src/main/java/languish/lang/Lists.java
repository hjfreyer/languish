package languish.lang;

import java.io.FileNotFoundException;

import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;
import languish.lambda.Lambda;
import languish.lambda.Term;

import com.google.common.collect.ImmutableList;

public class Lists {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/lists");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term map() {
    return Lambda.car(LIB);
  }

  public static Term reduce() {
    return Lambda.car(Lambda.cdr(LIB));
  }

  public static Term member() {
    return Lambda.car(Lambda.cdr(Lambda.cdr(LIB)));
  }

  private Lists() {
  }
}
