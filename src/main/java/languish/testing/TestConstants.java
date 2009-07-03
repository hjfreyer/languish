package languish.testing;

import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.prim.data.LInteger;
import languish.prim.data.LUnit;

public class TestConstants {
  public static final LUnit UNIT = LUnit.UNIT;
  public static final Tuple NULL = Lambda.data(LUnit.UNIT);

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

  public static final Tuple OMEGA =
      Lambda.abs(Lambda.app(Lambda.ref(1), Lambda.ref(1)));
  public static final Tuple LOOP = Lambda.app(OMEGA, OMEGA);

}
