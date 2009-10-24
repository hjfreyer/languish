package languish.lang;

import java.io.FileNotFoundException;

import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;

import com.google.common.collect.ImmutableList;

public class Lists {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Tuple LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/lists");
    } catch (FileNotFoundException e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Tuple map() {
    return Lambda.car(LIB);
  }

  public static Tuple reduce() {
    return Lambda.car(Lambda.cdr(LIB));
  }

  private Lists() {
  }
}
