package languish.base;

import static languish.base.Lambda.*;
import static languish.testing.CommonExps.*;
import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;

public class EqualityTest extends TestCase {

  private static final Tuple LIST_A =
      cons(data(TWO), cons(data(THREE), cons(data(FIVE), NULL)));

  private static final Tuple LIST_B =
      cons(data(THREE), cons(data(SIX), cons(data(TWO), NULL)));

  public enum Equals {
    INT_IDENTITY(data(TWO), data(TWO)),
    INT_VALUE(data(TWO), data(LInteger.of(2))),
    INT_INSIDE_APP(data(TWO), app(IDENTITY, data(LInteger.of(2)))),
    INT_INSIDE_ABS(abs(data(TWO)), abs(data(LInteger.of(2)))),
    INT_INSIDE_ABS_INSIDE_APP(abs(data(TWO)), app(IDENTITY, abs(data(TWO)))),
    INT_INSIDE_ABS_INSIDE_APP_SANDWICH(abs(data(TWO)), app(IDENTITY, abs(app(
        IDENTITY, data(TWO))))),
    INT_RESULT_OF_PRIM(data(FIVE), prim(DataFunctions.ADD, data(TWO),
        data(THREE))),

    CONS_BASIC(cons(data(TWO), data(THREE)), cons(data(TWO), data(THREE))),
    CONS_WITH_REDUCE(cons(app(IDENTITY, data(TWO)), data(THREE)), cons(
        data(TWO), app(IDENTITY, app(IDENTITY, data(THREE))))),
    CAR_VS_CDAR(app(CAR, LIST_B), app(CAR, app(CDR, LIST_A)));

    Tuple a;
    Tuple b;

    private Equals(Tuple a, Tuple b) {
      this.a = a;
      this.b = b;
    }

  }

  public enum DoesNotEqual {
    INT_CONSTANTS(data(TWO), data(THREE)),
    INT_INSIDE_DOUBLE_ABS(abs(data(TWO)), abs(abs(data(LInteger.of(2))))),

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
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda.reduce(exp));
      Tuple exp2 = eq(test.b, test.a);
      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda.reduce(exp2));
    }

    for (DoesNotEqual test : DoesNotEqual.values()) {
      Tuple exp = eq(test.a, test.b);
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda.reduce(exp));
      Tuple exp2 = eq(test.b, test.a);
      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda.reduce(exp2));
    }
  }
}
