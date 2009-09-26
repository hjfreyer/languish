package languish.libtesting;

import static languish.base.Lambda.abs;
import static languish.base.Lambda.app;
import static languish.base.Lambda.car;
import static languish.base.Lambda.cdr;
import static languish.base.Lambda.cons;
import static languish.base.Lambda.data;
import static languish.base.Lambda.prim;
import static languish.base.Lambda.ref;
import static languish.base.Util.convertJavaToPrimitive;
import static languish.libtesting.CommonTest.CAAR;
import static languish.libtesting.CommonTest.NULL;
import static languish.libtesting.CommonTest.OMEGA;
import static languish.testing.TestUtil.FIVE;
import static languish.testing.TestUtil.FOUR;
import static languish.testing.TestUtil.NINE;
import static languish.testing.TestUtil.THREE;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.primitives.DataFunctions;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.CommonExps;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

import com.google.common.collect.ImmutableList;

public class MapsTest extends TestCase {

  public static final Tuple MAP_PUT = //
  abs(abs(abs( // MAP, KEY, VAL
  cons(cons(ref(2), cons(ref(1), NULL)), ref(3)))));
  public static final String MAP_PUT_CODE = "[ABS [ABS [ABS [CONS [CONS [REF 2] [CONS [REF 1] [DATA []]]] [REF 3]]]]]";

  public static final Tuple MAP_GET = //
  app(OMEGA, // recursive
      abs(abs(abs(abs( // SELF, MAP, KEY, DEFAULT
      app(app(prim(DataFunctions.BRANCH, // IF
          eq(NULL, ref(3))), // MAP == NULL
          ref(1)), // RETURN DEFAULT, ELSE
          app(app(prim(DataFunctions.BRANCH, // IF
              // top of map key = KEY
              eq(ref(2), app(CAAR, ref(3)))), car(cdr(car(ref(3))))), // then
              // return
              // top of
              // map val
              app(app(app(app( // ELSE, recurse
                  ref(4), //
                  ref(4)), //
                  cdr(ref(3))), //
                  ref(2)), //
                  ref(1)))))))));

  public static final String MAP_GET_CODE = "[APP [ABS [APP [REF 1] [REF 1]]] [ABS [ABS [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [APP [APP [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]] [DATA []]] [REF 3]]] [REF 1]] [APP [APP [PRIM [DATA BRANCH] [APP [APP [ABS [ABS [APP [APP [PRIM [DATA BRANCH] [PRIM [DATA AND] [CONS [IS_PRIMITIVE [REF 1]] [IS_PRIMITIVE [REF 2]]]]] [PRIM [DATA DATA_EQUALS] [CONS [REF 1] [REF 2]]]] [DATA FALSE]]]] [REF 2]] [APP [ABS [CAR [CAR [REF 1]]]] [REF 3]]]] [CAR [CDR [CAR [REF 3]]]]] [APP [APP [APP [APP [REF 4] [REF 4]] [CDR [REF 3]]] [REF 2]] [REF 1]]]]]]]]]";

  @SuppressWarnings("unchecked")
  public enum Tests implements LanguishTestList {
    MP_CODE(MAP_PUT, //
        MAP_PUT_CODE, null, null, null),

    MG_CODE(MAP_GET, //
        MAP_GET_CODE, null, null, null),

    // PUT
    PUT_SINGLE_ELEMENT(
        app(app(app(MAP_PUT, NULL), data(LSymbol.of("FIVE"))), data(FIVE)), //
        "[APP [APP [APP " + MAP_PUT_CODE
            + " [DATA []]] [DATA \"FIVE\"]] [DATA 5]]", null, null,
        ImmutableList.of(ImmutableList.of(LSymbol.of("FIVE"), FIVE))),

    PUT_DOUBLE_ELEMENT(app(app(app(MAP_PUT, PUT_SINGLE_ELEMENT.expression),
        data(LSymbol.of("FOUR"))), data(FOUR)), //
        "[APP [APP [APP " + MAP_PUT_CODE + " " + PUT_SINGLE_ELEMENT.code
            + "] [DATA \"FOUR\"]] [DATA 4]]", null, null, ImmutableList.of(
            ImmutableList.of(LSymbol.of("FOUR"), FOUR), ImmutableList.of(
                LSymbol.of("FIVE"), FIVE))),

    PUT_OVERWRITE(app(app(app(MAP_PUT, PUT_DOUBLE_ELEMENT.expression),
        data(LSymbol.of("FIVE"))), data(THREE)), //
        "[APP [APP [APP " + MAP_PUT_CODE + " " + PUT_DOUBLE_ELEMENT.code
            + "] [DATA \"FIVE\"]] [DATA 3]]", null, null, ImmutableList.of(
            ImmutableList.of(LSymbol.of("FIVE"), THREE), ImmutableList.of(
                LSymbol.of("FOUR"), FOUR), ImmutableList.of(LSymbol.of("FIVE"),
                FIVE))),

    // GET
    GET_FROM_EMPTY(app(app(app(MAP_GET, NULL), data(LSymbol.of("FOO"))),
        data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP " + MAP_GET_CODE + " [DATA []]] [DATA \"FOO\"]] "
            + "[DATA \"DEFAULT\"]]", null, LSymbol.of("DEFAULT"), null),

    GET_FROM_SINGLETON_CORRECT(
        app(app(app(MAP_GET, convertJavaToPrimitive(ImmutableList
            .of(ImmutableList.of("FIVE", 5)))), data(LSymbol.of("FIVE"))),
            data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP " + MAP_GET_CODE
            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
            + "[DATA []]]] [DATA \"FIVE\"]] [DATA \"DEFAULT\"]]", null,
        LInteger.of(5), null),

    GET_FROM_SINGLETON_INCORRECT(app(app(app(MAP_GET,
        convertJavaToPrimitive(ImmutableList.of(ImmutableList.of("FIVE", 5)))),
        data(LSymbol.of("BAD"))), data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP " + MAP_GET_CODE
            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
            + "[DATA []]]] [DATA \"BAD\"]] [DATA \"DEFAULT\"]]", null, LSymbol
            .of("DEFAULT"), null),

    GET_FROM_DOUBLE_A(
        app(app(app(MAP_GET, convertJavaToPrimitive(ImmutableList.of(
            ImmutableList.of("FIVE", 5), ImmutableList.of("FOUR", 4)))),
            data(LSymbol.of("FIVE"))), data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP "
            + MAP_GET_CODE
            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] [DATA []]]]] "
            + "[DATA \"FIVE\"]] [DATA \"DEFAULT\"]]", null, LInteger.of(5),
        null),

    GET_FROM_DOUBLE_B(
        app(app(app(MAP_GET, convertJavaToPrimitive(ImmutableList.of(
            ImmutableList.of("FIVE", 5), ImmutableList.of("FOUR", 4)))),
            data(LSymbol.of("FOUR"))), data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP "
            + MAP_GET_CODE
            + " [CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] [DATA []]]]] "
            + "[DATA \"FOUR\"]] [DATA \"DEFAULT\"]]", null, LInteger.of(4),
        null),

    GET_FROM_OVERWRITE(
        app(app(app(MAP_GET, convertJavaToPrimitive(ImmutableList.of(
            ImmutableList.of("FOUR", 3), ImmutableList.of("FIVE", 5),
            ImmutableList.of("FOUR", 4)))), data(LSymbol.of("FOUR"))),
            data(LSymbol.of("DEFAULT"))), //
        "[APP [APP [APP " + MAP_GET_CODE + " [CONS "
            + "[CONS [DATA \"FOUR\"] [CONS [DATA 3] [DATA []]]] "
            + "[CONS [CONS [DATA \"FIVE\"] [CONS [DATA 5] [DATA []]]] "
            + "[CONS [CONS [DATA \"FOUR\"] [CONS [DATA 4] [DATA []]]] "
            + "[DATA []]]]]] [DATA \"FOUR\"]] [DATA \"DEFAULT\"]]", null,
        LInteger.of(3), null),

    STORE_ABS(
        app(app(app(MAP_PUT, NULL), data(LSymbol.of("ADD_ONE"))), abs(prim(
            DataFunctions.ADD, cons(ref(1), data(LInteger.of(1)))))), //
        "[APP [APP [APP " + MAP_PUT_CODE + " [DATA []]] "
            + "[DATA \"ADD_ONE\"]] "
            + "[ABS [PRIM [DATA ADD] [CONS [REF 1] [DATA 1]]]]]", null, null,
        null),

    LOAD_ABS(
        app( //
            app(app(app(MAP_GET, STORE_ABS.expression), data(LSymbol
                .of("ADD_ONE"))), data(LSymbol.of("DEFAULT"))), data(LInteger
                .of(8))), //
        "[APP [APP [APP [APP " + MAP_GET_CODE + " " + STORE_ABS.code + "] "
            + "[DATA \"ADD_ONE\"]] " + "[DATA \"DEFAULT\"]] " + "[DATA 8]]",
        null, NINE, null),

    ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final LObject reducedCompletely;
    private final List<?> listContents;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        LObject reducedCompletely, List<?> listContents) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
      this.listContents = listContents;
    }

    public Tuple getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Tuple getReducedOnce() {
      return reducedOnce;
    }

    public LObject getReducedCompletely() {
      return reducedCompletely;
    }

    public List<?> getListContents() {
      return listContents;
    }

  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
  }

  private static Tuple eq(Tuple a, Tuple b) {
    return app(app(CommonExps.EQUALS, a), b);
  }

}
