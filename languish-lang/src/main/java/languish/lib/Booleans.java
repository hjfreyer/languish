package languish.lib;

import languish.base.Terms;
import languish.base.Term;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;
import languish.interpreter.error.DependencyUnavailableError;

import com.google.common.collect.ImmutableList;

public class Booleans {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/booleans");
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term and() {
    return Terms.car(LIB);
  }

  public static Term or() {
    return Terms.car(Terms.cdr(LIB));
  }

  public static Term not() {
    return Terms.car(Terms.cdr(Terms.cdr(LIB)));
  }

  private Booleans() {
  }
}
