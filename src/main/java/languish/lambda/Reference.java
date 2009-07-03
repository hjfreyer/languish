package languish.lambda;

import languish.prim.data.LInteger;

public class Reference {

  public static Tuple to(int i) {
    return Tuple.of(Lambda.REF, LInteger.of(i));
  }

}