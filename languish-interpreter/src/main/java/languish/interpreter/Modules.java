package languish.interpreter;

import static languish.base.Terms.*;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

public class Modules {

  public static Term value(Term value) {
    return Terms.cons(Terms.primObj("VALUE"), Terms.cons(value, Term.NULL));
  }

  public static Term load(String depname, Term function) {
    return Terms.cons(
        Terms.primitive(new Primitive("LOAD")),
        cons(cons(Terms.primitive(new Primitive(depname)), cons(
            function,
            Term.NULL)), Term.NULL));
  }

  public static Term loadAndReturn(String depname) {
    return Modules.load(depname, abs(Modules.value(ref(3))));
  }

  public static Term reduceAndApply(Term reduce, Term function) {
    return Terms.cons(Terms.primObj("REDUCE_AND_APPLY"), cons(cons(
        reduce,
        cons(function, Term.NULL)), Term.NULL));
  }

  private Modules() {
  }
}
