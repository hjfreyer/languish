package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.interpreter.error.DependencyUnavailableError;
import languish.lib.testing.LibTestUtil;

public class Integers {
  private static final Term LIB;

  static {
    try {
      LIB = LibTestUtil.STANDARD_INCLUDE.getResource("bootstrap/integers");
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
