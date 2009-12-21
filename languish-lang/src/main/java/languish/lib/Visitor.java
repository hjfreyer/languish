package languish.lib;

import static languish.base.Terms.*;
import languish.base.Term;
import languish.base.Terms;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;
import languish.interpreter.Interpreter;
import languish.interpreter.Modules;
import languish.interpreter.error.DependencyUnavailableError;

import com.google.common.collect.ImmutableList;

public class Visitor {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));
  private static final Term LIB;

  static {
    try {
      LIB =
          Interpreter.reduceModuleCompletely(
              Modules.load("bootstrap/visitor", abs(ref(1))),
              DEPMAN);
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
