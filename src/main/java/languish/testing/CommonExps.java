package languish.testing;

import static languish.lambda.Lambda.*;
import languish.lambda.Term;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;

public class CommonExps {

  // BASIC FUNCS
  public static final Term IDENTITY = abs(ref(1));
  public static final String IDENTITY_CODE = "[ABS [REF 1]]";

  public static final Term EQUALS = abs(abs( //
      app(app(nativeApply(DataFunctions.BRANCH, //
          nativeApply(DataFunctions.AND, cons(is_prim(ref(1)), is_prim(ref(2))))), //
          nativeApply(DataFunctions.DATA_EQUALS, cons(ref(1), ref(2)))), //
          primitive(LBoolean.FALSE))));
  public static final String EQUALS_CODE =
      "[ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]]";

  // RECURSION
  public static final Term OMEGA = abs(app(ref(1), ref(1)));
  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";

  public static final Term LOOP = app(OMEGA, OMEGA);
  public static final String LOOP_CODE =
      "[APP " + OMEGA_CODE + " " + OMEGA_CODE + "]";

  private CommonExps() {}

}
