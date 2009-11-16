package languish.lambda;

import static languish.lambda.Term.NULL;
import static languish.testing.TestUtil.*;
import static languish.util.Lambda.*;
import junit.framework.TestCase;
import languish.primitives.LInteger;
import languish.testing.LanguishTestCase;
import languish.testing.TestUtil;
import languish.util.JavaWrapper;
import languish.util.Lambda;

import com.google.common.collect.ImmutableList;

public class NativeFunctionTest extends TestCase {
  public static NativeFunction TRIVIAL = new NativeFunction() {
    public JavaWrapper apply(JavaWrapper arg) {
      return JavaWrapper.of(42);
    }
  };

  public static NativeFunction IDENTITY = new NativeFunction() {
    public JavaWrapper apply(JavaWrapper arg) {
      return arg;
    }
  };

  public enum Tests implements LanguishTestCase {
    TRIVIAL_NATIVE_FUNC( //
        nativeApply(TRIVIAL, NULL),
        null,
        primitive(LInteger.of(42)),
        JavaWrapper.of(42)),

    TRIVIAL_NATIVE_WITH_REDUCE( //
        nativeApply(TRIVIAL, app(abs(primitive(FOUR)), NULL)),
        null,
        nativeApply(TRIVIAL, primitive(FOUR)),
        JavaWrapper.of(42)),

    TRIVIAL_NATIVE_WITH_LIST( //
        Lambda.nativeApply(TRIVIAL, cons(primitive(THREE), cons(
            primitive(FOUR), NULL))),
        null,
        Lambda.primitive(LInteger.of(42)),
        JavaWrapper.of(42)),

    IDENT_NATIVE_FUNC( //
        nativeApply(IDENTITY, NULL),
        null,
        NULL,
        JavaWrapper.of(ImmutableList.of())),

    IDENT_NATIVE_WITH_REDUCE( //
        nativeApply(IDENTITY, app(abs(primitive(FOUR)), NULL)),
        null,
        nativeApply(IDENTITY, primitive(FOUR)),
        JavaWrapper.of(4)),

    IDENT_NATIVE_WITH_LIST( //
        Lambda.nativeApply(IDENTITY, cons(primitive(THREE), cons(
            primitive(FOUR), NULL))),
        null,
        cons(primitive(THREE), cons(primitive(FOUR), NULL)),
        JavaWrapper.of(ImmutableList.of(3, 4))),

    ;

    private final Term expression;
    private final String code;
    private final Term reducedOnce;
    private final JavaWrapper reducedCompletely;

    private Tests(Term expression, String code, Term reducedOnce,
        JavaWrapper reducedCompletely) {
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

    public JavaWrapper getReducedCompletely() {
      return reducedCompletely;
    }
  }

  public void test() {
    for (LanguishTestCase test : Tests.values()) {
      TestUtil.assertLanguishTestCase(test);
    }
  }
}
