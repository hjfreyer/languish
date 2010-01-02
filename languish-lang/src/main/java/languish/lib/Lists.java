package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.testing.LibTestUtil;

public class Lists {

  private static final Term LIB = LibTestUtil.loadLib("bootstrap/lists");

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
