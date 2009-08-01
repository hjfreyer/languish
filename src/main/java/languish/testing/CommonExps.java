package languish.testing;

import static languish.base.Lambda.*;
import languish.base.Tuple;
import languish.primitives.DataFunctions;

public class CommonExps {

  // BASIC FUNCS
  public static final Tuple IDENTITY = abs(ref(1));
  public static final String IDENTITY_CODE = "[ABS [REF 1]]";

  // RECURSION
  public static final Tuple OMEGA = abs(app(ref(1), ref(1)));
  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";

  public static final Tuple LOOP = app(OMEGA, OMEGA);
  public static final String LOOP_CODE =
      "[APP " + OMEGA_CODE + " " + OMEGA_CODE + "]";

  // LISTS
  public static final Tuple EMPTY_LIST = data(Tuple.of());
  public static final String EMPTY_LIST_CODE = "[DATA []]";

  public static final Tuple CAR = abs(get(1, ref(1)));
  public static final String CAR_CODE = "[ABS [GET [DATA 1] [REF 1]]]";

  public static final Tuple CDR = abs(get(2, ref(1)));
  public static final String CDR_CODE = "[ABS [GET [DATA 2] [REF 1]]]";

  public static final Tuple PUSHL = abs(abs(cons(ref(1), ref(2))));
  public static final String PUSHL_CODE = "[ABS [ABS [CONS [REF 1] [REF 2]]]]";

  public static final Tuple ADDL = app(OMEGA, // ========= RECURSIVE
      abs( // ============= SELF
      abs( // ============= LIST
      abs( // ============= ITEM
      app(app(prim(DataFunctions.BRANCH, eq(EMPTY_LIST, ref(2))), // =============
          // If
          // (LIST
          // == [])
          cons(ref(1), EMPTY_LIST)), // Then: return [ITEM []]
          cons(app(CAR, ref(2)), // === Else, return (CAR LIST) ::
              // ======================== ADDL(ADDL, (CDR LIST), ITEM)
              app(app(app(ref(3), ref(3)), app(CDR, ref(2))), ref(1))))))));

  public static final String ADDL_CODE =
      "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [EQUALS [DATA []] [REF 2]]] [CONS [REF 1] [DATA []]]] [CONS [APP [ABS [GET [DATA 1] [REF 1]]] [REF 2]] [APP [APP [APP [REF 3] [REF 3]] [APP [ABS [GET [DATA 2] [REF 1]]] [REF 2]]] [REF 1]]]]]]]]";

  private CommonExps() {}

}
