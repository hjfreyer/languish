package languish.testing;

import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Operation;
import languish.base.Tuple;
import languish.interpreter.BuiltinParser;
import languish.primitives.LInteger;

public class TestUtil {
  // public static final LUnit UNIT = LUnit.UNIT;
  // public static final Tuple NULL = Lambda.data(Tuple.of());

  public static final LInteger ZERO = LInteger.of(0);
  public static final LInteger ONE = LInteger.of(1);
  public static final LInteger TWO = LInteger.of(2);
  public static final LInteger THREE = LInteger.of(3);
  public static final LInteger FOUR = LInteger.of(4);
  public static final LInteger FIVE = LInteger.of(5);
  public static final LInteger SIX = LInteger.of(6);
  public static final LInteger SEVEN = LInteger.of(7);
  public static final LInteger EIGHT = LInteger.of(8);
  public static final LInteger NINE = LInteger.of(9);
  public static final LInteger TEN = LInteger.of(10);
  public static final LInteger ELEVEN = LInteger.of(11);
  public static final LInteger TWELVE = LInteger.of(12);
  public static final LInteger THIRTEEN = LInteger.of(13);
  public static final LInteger FOURTEEN = LInteger.of(14);
  public static final LInteger FIFTEEN = LInteger.of(15);

  public static final Tuple IDENT = Lambda.abs(Lambda.ref(1));

  public static Tuple reduceTupleOnce(LObject tuple) {
    Tuple t = (Tuple) tuple.deepClone();
    Operation op = (Operation) t.getFirst();

    Tuple result = op.reduceOnce(t);

    return result;
  }

  public static void assertList(String msg, List<?> contents, Tuple exp) {

    for (Object obj : contents) {
      Tuple car = Lambda.car(exp);
      exp = Lambda.cdr(exp);

      if (obj instanceof List) {
        assertList(msg, (List<?>) obj, car);
      } else if (obj instanceof LObject) {
        TestCase.assertEquals(msg, obj, Lambda.reduceToDataValue(car));
      }
    }

    TestCase.assertEquals(msg, Tuple.of(), Lambda.reduceToDataValue(exp));
  }

  public static void testExpressions(
      List<? extends LanguishTestList> expressions) {

    for (LanguishTestList expToTest : expressions) {
      Tuple exp = expToTest.getExpression();
      String code = expToTest.getCode();
      Tuple reducedOnce = expToTest.getReducedOnce();
      LObject reducedCompletely = expToTest.getReducedCompletely();
      List<?> listContents = expToTest.getListContents();

      if (code != null) {
        // TOSTRING
        TestCase.assertEquals("on test " + expToTest.name()
            + " - getCodeForExpression() does not match code:", code, Canonizer
            .getCodeForExpression(exp));

        // PARSE
        LObject parsed = BuiltinParser.SINGLE_TUPLE.parse(code);

        TestCase.assertEquals("on test " + expToTest.name()
            + " - code does not parse to given expression:", exp, parsed);
      }
      // REDUCE ONCE
      if (reducedOnce != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not reduce once to given value:",
            reducedOnce, reduceTupleOnce(exp.deepClone()));
      }

      // REDUCE COMPLETELY
      if (reducedCompletely != null) {
        TestCase.assertEquals("on test " + expToTest.name()
            + " - expression does not ultimately reduce to given value:",
            reducedCompletely, Lambda.reduceToDataValue(exp));
      }

      // LIST CONTENTS
      if (listContents != null) {
        assertList("on test " + expToTest.name()
            + " - expression does not correspond to given list:", listContents,
            exp);
      }
    }
  }
}
