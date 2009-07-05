package languish.testing;

import static languish.base.Lambda.*;
import languish.base.LObject;
import languish.base.Tuple;
import languish.prim.data.DataFunctions;

public class CommonExps {

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
  public static final String CAR_CODE = "[ABS [GET 1 [REF 1]]]";

  public static final Tuple CDR = abs(get(2, ref(1)));
  public static final String CDR_CODE = "[ABS [GET 2 [REF 1]]]";

  public static final Tuple PUSHL = abs(abs(pair(ref(1), ref(2))));
  public static final String PUSHL_CODE = "[ABS [ABS [PAIR [REF 1] [REF 2]]]]";

  public static final Tuple ADDL =
      app(OMEGA, // ========= RECURSIVE
          abs( // ============= SELF
          abs( // ============= LIST
          abs( // ============= ITEM
          app(app(prim(DataFunctions.BRANCH, prim(LObject.EQUALS, pair(
              EMPTY_LIST, ref(2)))), // ============= If (LIST == [])
              pair(ref(1), EMPTY_LIST)), // Then: return [ITEM []]
              pair(app(CAR, ref(2)), // === Else, return (CAR LIST) ::
                  // ======================== ADDL(ADDL, (CDR LIST), ITEM)
                  app(app(app(ref(3), ref(3)), app(CDR, ref(2))), ref(1))))))));

  public static final String ADDL_CODE =
      "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [APP [APP [PRIM BRANCH [PRIM EQUALS [PAIR [DATA []] [REF 2]]]] [PAIR [REF 1] [DATA []]]] [PAIR [APP [ABS [GET 1 [REF 1]]] [REF 2]] [APP [APP [APP [REF 3] [REF 3]] [APP [ABS [GET 2 [REF 1]]] [REF 2]]] [REF 1]]]]]]]]";

  private CommonExps() {}

}
