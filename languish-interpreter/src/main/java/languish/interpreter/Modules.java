package languish.interpreter;

import static languish.base.Terms.*;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

public class Modules {

  public static Term load(String depname) {
    return Terms.cons(Terms.primitive(new Primitive("LOAD")), cons(
        cons(Terms.primitive(new Primitive(depname)), cons(
            abs(ref(1)),
            Term.NULL)),
        Term.NULL));
  }

  private Modules() {
  }
}
