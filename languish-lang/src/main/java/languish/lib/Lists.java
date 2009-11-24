package languish.lib;

import languish.base.Terms;
import languish.base.Term;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;
import languish.interpreter.error.DependencyUnavailableError;

import com.google.common.collect.ImmutableList;

public class Lists {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/lists");
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term map() {
    return Terms.car(LIB);
  }

  public static Term reduce() {
    return Terms.car(Terms.cdr(LIB));
  }

  public static Term member() {
    return Terms.car(Terms.cdr(Terms.cdr(LIB)));
  }

  private Lists() {
  }
}
