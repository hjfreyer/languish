package languish.lib;

import languish.base.Terms;
import languish.base.Term;
import languish.interpreter.DependencyManager;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.error.DependencyUnavailableError;

import com.google.common.collect.ImmutableList;

public class Visitor {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB = DEPMAN.getResource("lang/visitor");
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }

  public static Term visitTree() {
    return Terms.car(LIB);
  }

  private Visitor() {
  }
}
