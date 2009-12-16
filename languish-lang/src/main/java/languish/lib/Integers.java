package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;
import languish.interpreter.error.DependencyUnavailableError;

import com.google.common.collect.ImmutableList;

public class Integers {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("bootstrap/integers");
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term add() {
    return Terms.car(LIB);
  }

  public static Term multiply() {
    return Terms.car(Terms.cdr(LIB));
  }

  private Integers() {
  }
}
