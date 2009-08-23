package languish.testing;

import static languish.base.Lambda.*;
import languish.base.Tuple;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;

public class CommonExps {

  // BASIC FUNCS
  public static final Tuple IDENTITY = abs(ref(1));
  public static final String IDENTITY_CODE = "[ABS [REF 1]]";

  public static final Tuple EQUALS = abs(abs( //
      app(app(prim(DataFunctions.BRANCH, //
          prim(DataFunctions.AND, cons(is_prim(ref(1)), is_prim(ref(2))))), //
          prim(DataFunctions.DATA_EQUALS, cons(ref(1), ref(2)))), //
          data(LBoolean.FALSE))));
  public static final String EQUALS_CODE =
      "[ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]]";

  // RECURSION
  public static final Tuple OMEGA = abs(app(ref(1), ref(1)));
  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";

  public static final Tuple LOOP = app(OMEGA, OMEGA);
  public static final String LOOP_CODE =
      "[APP " + OMEGA_CODE + " " + OMEGA_CODE + "]";

  private CommonExps() {}

}
