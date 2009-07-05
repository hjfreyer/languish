package languish.prim.data;

import static languish.lambda.Lambda.*;
import static languish.testing.TestConstants.*;
import junit.framework.TestCase;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;

public class ListTest extends TestCase {

  public static final Tuple CAR = abs(get(1, ref(1)));

  public static final Tuple CDR = abs(get(2, ref(1)));

  public void testEmptyList() {
    assertListContents(data(Tuple.of()));
  }

  public void testSingleElementList() {
    Tuple list = pair(data(TWO), data(Tuple.of()));
    assertListContents(list, TWO);
  }

  public void testDoubleElementList() {
    Tuple list = pair(data(THREE), pair(data(TWO), NULL));
    assertListContents(list, THREE, TWO);
  }

  public void testTripleElementList() {
    Tuple list = pair(data(THREE), pair(data(FOUR), pair(data(TWO), NULL)));
    assertListContents(list, THREE, FOUR, TWO);
  }

  public void testCar() {
    Tuple list = pair(data(THREE), pair(data(FOUR), pair(data(TWO), NULL)));

    list = app(CAR, list);

    assertEquals(THREE, Lambda.reduce(list));
  }

  public void testCdr() {
    Tuple list = pair(data(THREE), pair(data(FOUR), pair(data(TWO), NULL)));
    assertListContents(list, THREE, FOUR, TWO);

    list = app(CDR, list);

    assertListContents(list, FOUR, TWO);
  }

  public void testCarCdr() {
    Tuple list = pair(data(THREE), pair(data(FOUR), pair(data(TWO), NULL)));
    assertListContents(list, THREE, FOUR, TWO);

    list = app(CAR, app(CDR, list));

    assertEquals(FOUR, Lambda.reduce(list));
  }

  public void assertListContents(Tuple list, LObject... contents) {
    for (int i = 0; i < contents.length; i++) {
      Tuple car = Lambda.get(1, list);

      LObject obj = Lambda.reduce(car);
      list = Lambda.get(2, list);

      assertEquals("list[" + i + "] invalid", contents[i], obj);
    }

    assertEquals("list was longer than expected", Tuple.of(), Lambda
        .reduce(list));
  }
}
