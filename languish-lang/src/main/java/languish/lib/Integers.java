package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.testing.LibTestUtil;

public class Integers {

  private static final Term LIB = LibTestUtil.loadLib("bootstrap/integers");

  public static Term add() {
    return Terms.car(LIB);
  }

  public static Term multiply() {
    return Terms.car(Terms.cdr(LIB));
  }

  private Integers() {
  }
}
