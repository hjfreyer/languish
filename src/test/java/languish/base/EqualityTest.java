package languish.base;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.IDENTITY;
import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;
import languish.testing.CommonExps;

public class EqualityTest extends TestCase {

  public enum Equals {
    INT_IDENTITY(data(TWO), data(TWO)),
    INT_VALUE(data(TWO), data(LInteger.of(2))),
    INT_INSIDE_APP(data(TWO), app(IDENTITY, data(LInteger.of(2)))),
    INT_RESULT_OF_PRIM(data(FIVE), prim(DataFunctions.ADD, cons(data(TWO),
        data(THREE)))),

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
      Tuple exp = eq(test.a, test.b);
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda
          .reduceToDataValue(exp));
      Tuple exp2 = eq(test.b, test.a);
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda
          .reduceToDataValue(exp2));
    }

    for (DoesNotEqual test : DoesNotEqual.values()) {
      Tuple exp = eq(test.a, test.b);
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda
          .reduceToDataValue(exp));
      Tuple exp2 = eq(test.b, test.a);
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda
          .reduceToDataValue(exp2));
    }
  }

  private Tuple eq(Tuple a, Tuple b) {
    return app(app(CommonExps.EQUALS, a), b);
  }
}
