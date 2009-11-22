package languish.base;

import static languish.base.Term.NULL;
import static languish.testing.TestUtil.*;
import static languish.util.Lambda.*;
import junit.framework.TestCase;
import languish.base.Term;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;

public class ConsTest extends TestCase {
  public enum Tests implements LanguishTestCase {
    GET_NULL( //
        NULL,
        "NULL",
        null,
        PrimitiveTree.copyOf(ImmutableList.of())),

    CREATE_SINGLETON( //
        cons(primitive(FIVE), NULL),
        "[ABS [APP [APP [REF 1 NULL] [PRIMITIVE 5 NULL]] NULL] NULL]",
        null,
        PrimitiveTree.copyOf(ImmutableList.of(5))),

    CAR_SINGLETON( //
        car(cons(primitive(FIVE), NULL)),
        "[APP [ABS [APP [APP [REF 1 NULL] [PRIMITIVE 5 NULL]] NULL] NULL] "
            + "[ABS [ABS [REF 2 NULL] NULL] NULL]]",
        app(app(abs(abs(ref(2))), primitive(FIVE)), Term.NULL),
        PrimitiveTree.copyOf(5)),

    GET_PAIR_WITH_SINGLE_REF( //
        app(abs(cons(ref(2), cons(ref(3), NULL))), primitive(FOUR)),
        "[APP [ABS [ABS [APP [APP [REF 1 NULL] "
            + "[REF 2 NULL]] [ABS [APP [APP [REF 1 NULL] "
            + "[REF 3 NULL]] NULL] NULL]] NULL] NULL] [PRIMITIVE 4 NULL]]",
        cons(primitive(FOUR), cons(primitive(FOUR), NULL)),
        PrimitiveTree.copyOf(ImmutableList.of(4, 4))),

    GET_PAIR_WITH_DOUBLE_REF( //
        app(app(abs(abs(cons(ref(2), cons(ref(4), NULL)))), primitive(FOUR)),
            primitive(FIVE)),
        "[APP [APP [ABS [ABS [ABS [APP [APP [REF 1 NULL] [REF 2 NULL]] "
            + "[ABS [APP [APP [REF 1 NULL] [REF 4 NULL]] NULL] NULL]] "
            + "NULL] NULL] NULL] [PRIMITIVE 4 NULL]] [PRIMITIVE 5 NULL]]",
        app(abs(cons(ref(2), cons(primitive(FOUR), NULL))), primitive(FIVE)),
        PrimitiveTree.copyOf(ImmutableList.of(5, 4))),

    GET_NESTED_LIST( //
        cons(cons(primitive(FIVE), NULL), cons(primitive(FOUR), NULL)),
        "[ABS [APP [APP [REF 1 NULL] [ABS [APP [APP [REF 1 NULL] "
            + "[PRIMITIVE 5 NULL]] NULL] NULL]] [ABS [APP [APP [REF 1 NULL] "
            + "[PRIMITIVE 4 NULL]] NULL] NULL]] NULL]",
        null,
        PrimitiveTree.copyOf(ImmutableList.of(ImmutableList.of(5), 4))),

    GET_LIST_WITH_UNREDUCED_CAR( //
        cons(app(IDENT, primitive(FIVE)), cons(primitive(FOUR), NULL)),
        "[ABS [APP [APP [REF 1 NULL] [APP [ABS [REF 1 NULL] NULL] "
            + "[PRIMITIVE 5 NULL]]] [ABS [APP [APP [REF 1 NULL] "
            + "[PRIMITIVE 4 NULL]] NULL] NULL]] NULL]",
        null,
        PrimitiveTree.copyOf(ImmutableList.of(5, 4))),

    GET_LIST_WITH_UNREDUCED_CDR( //
        cons(primitive(FIVE), app(IDENT, cons(primitive(FOUR), NULL))),
        "[ABS [APP [APP [REF 1 NULL] [PRIMITIVE 5 NULL]] "
            + "[APP [ABS [REF 1 NULL] NULL] [ABS [APP [APP [REF 1 NULL] "
            + "[PRIMITIVE 4 NULL]] NULL] NULL]]] NULL]",
        null,
        PrimitiveTree.copyOf(ImmutableList.of(5, 4))),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final PrimitiveTree reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        PrimitiveTree reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Term getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Term getReducedOnce() {
      return reducedOnce;
    }

    public PrimitiveTree getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
