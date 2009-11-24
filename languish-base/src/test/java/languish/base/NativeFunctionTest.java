package languish.base;

import static languish.base.Terms.*;
import static languish.base.Term.NULL;
import static languish.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.base.Term;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;

public class NativeFunctionTest extends TestCase {
  public static NativeFunction TRIVIAL = new NativeFunction() {
    public PrimitiveTree apply(PrimitiveTree arg) {
      return PrimitiveTree.copyOf(42);
    }
  };

  public static NativeFunction IDENTITY = new NativeFunction() {
    public PrimitiveTree apply(PrimitiveTree arg) {
      return arg;
    }
  };

  public enum Tests implements LanguishTestCase {
    TRIVIAL_NATIVE_FUNC( //
        nativeApply(TRIVIAL, NULL),
        null,
        primitive(new Primitive(42)),
        PrimitiveTree.copyOf(42)),

    TRIVIAL_NATIVE_WITH_REDUCE( //
        nativeApply(TRIVIAL, app(abs(primitive(FOUR)), NULL)),
        null,
        nativeApply(TRIVIAL, primitive(FOUR)),
        PrimitiveTree.copyOf(42)),

    TRIVIAL_NATIVE_WITH_LIST( //
        Terms.nativeApply(TRIVIAL, cons(primitive(THREE), cons(
            primitive(FOUR), NULL))),
        null,
        Terms.primitive(new Primitive(42)),
        PrimitiveTree.copyOf(42)),

    IDENT_NATIVE_FUNC( //
        nativeApply(IDENTITY, NULL),
        null,
        NULL,
        PrimitiveTree.copyOf(ImmutableList.of())),

    IDENT_NATIVE_WITH_REDUCE( //
        nativeApply(IDENTITY, app(abs(primitive(FOUR)), NULL)),
        null,
        nativeApply(IDENTITY, primitive(FOUR)),
        PrimitiveTree.copyOf(4)),

    IDENT_NATIVE_WITH_LIST( //
        Terms.nativeApply(IDENTITY, cons(primitive(THREE), cons(
            primitive(FOUR), NULL))),
        null,
        cons(primitive(THREE), cons(primitive(FOUR), NULL)),
        PrimitiveTree.copyOf(ImmutableList.of(3, 4))),

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
