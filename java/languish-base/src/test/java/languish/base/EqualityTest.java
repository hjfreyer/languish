package languish.base;
//package languish.lambda;
//
//import static languish.testing.CommonExps.IDENTITY;
//import static languish.testing.TestUtil.*;
//import static languish.util.Lambda.*;
//import junit.framework.TestCase;
//import languish.lambda.Term;
//import languish.primitives.DataFunctions;
//import languish.primitives.LBoolean;
//import languish.primitives.LInteger;
//import languish.testing.CommonExps;
//import languish.util.Lambda;
//
//public class EqualityTest extends TestCase {
//
//  public enum Equals {
//    INT_IDENTITY(primitive(TWO), primitive(TWO)),
//    INT_VALUE(primitive(TWO), primitive(LInteger.of(2))),
//    INT_INSIDE_APP(primitive(TWO), app(IDENTITY, primitive(LInteger.of(2)))),
//    INT_RESULT_OF_PRIM(primitive(FIVE), nativeApply(DataFunctions.ADD, cons(primitive(TWO),
//        primitive(THREE)))),
//
//    ;
//
//    Term a;
//    Term b;
//
//    private Equals(Term a, Term b) {
//      this.a = a;
//      this.b = b;
//    }
//
//  }
//
//  public enum DoesNotEqual {
//    INT_CONSTANTS(primitive(TWO), primitive(THREE)),
//
//    ;
//    Term a;
//    Term b;
//
//    private DoesNotEqual(Term a, Term b) {
//      this.a = a;
//      this.b = b;
//    }
//
//  }
//
//  public void test() {
//    for (Equals test : Equals.values()) {
//      Term exp = eq(test.a, test.b);
//      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda
//          .reduceToDataValue(exp));
//      Term exp2 = eq(test.b, test.a);
//      assertEquals("EQUALS." + test.name(), LBoolean.TRUE, Lambda
//          .reduceToDataValue(exp2));
//    }
//
//    for (DoesNotEqual test : DoesNotEqual.values()) {
//      Term exp = eq(test.a, test.b);
//      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda
//          .reduceToDataValue(exp));
//      Term exp2 = eq(test.b, test.a);
//      assertEquals("DNE." + test.name(), LBoolean.FALSE, Lambda
//          .reduceToDataValue(exp2));
//    }
//  }
//
//  private Term eq(Term a, Term b) {
//    return app(app(CommonExps.EQUALS, a), b);
//  }
// }
