package languish.base;

import static languish.base.Lambda.*;
import static languish.primitives.DataFunctions.DATA_EQUALS;
import static languish.testing.CommonExps.IDENTITY;
import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;

public class EqualityTest extends TestCase {

  public enum Equals {
    INT_IDENTITY(data(TWO), data(TWO)),
    INT_VALUE(data(TWO), data(LInteger.of(2))),
    INT_INSIDE_APP(data(TWO), app(IDENTITY, data(LInteger.of(2)))),
    INT_RESULT_OF_PRIM(data(FIVE), prim(DataFunctions.ADD, Util.listify(
        data(TWO), data(THREE)))),

    ;

    Tuple a;
    Tuple b;

    private Equals(Tuple a, Tuple b) {
      this.a = a;
      this.b = b;
    }

  }

  public enum DoesNotEqual {
    INT_CONSTANTS(data(TWO), data(THREE)),

    ;
    Tuple a;
    Tuple b;

    private DoesNotEqual(Tuple a, Tuple b) {
      this.a = a;
      this.b = b;
    }

  }

  public void test() {
    for (Equals test : Equals.values()) {
      Tuple exp = prim(DATA_EQUALS, cons(test.a, test.b));
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda.reduce(exp));
      Tuple exp2 = prim(DATA_EQUALS, cons(test.b, test.a));
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda.reduce(exp2));
    }

    for (DoesNotEqual test : DoesNotEqual.values()) {
      Tuple exp = prim(DATA_EQUALS, cons(test.a, test.b));
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda.reduce(exp));
      Tuple exp2 = prim(DATA_EQUALS, cons(test.b, test.a));
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda.reduce(exp2));
    }
  }
}
