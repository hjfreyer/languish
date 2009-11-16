package languish.lang;

import static languish.util.Lambda.*;
import junit.framework.TestCase;
import languish.lambda.Term;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.TestUtil;
import languish.util.Util;

import com.google.common.collect.ImmutableList;

public class ListsTest extends TestCase {

  private static final Term ADD_ONE =
      abs(nativeApply(DataFunctions.ADD, cons(primitive(LInteger.of(1)), ref(1))));

  public void testMapEmpty() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of());

    TestUtil.assertReducesToData(Term.of(), app(app(Lists.map(), ADD_ONE),
        list));
  }

  public void testMapIntegers() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 10, 12, 3));

    TestUtil.assertReducesTo(Util.convertJavaObjectToTerm(ImmutableList.of(5,
        11, 13, 4)), app(app(Lists.map(), ADD_ONE), list));
  }

  @SuppressWarnings("unchecked")
  public void testMapSingletonWrapper() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 10, 12, 3));

    TestUtil.assertReducesTo(Util.convertJavaObjectToTerm(ImmutableList.of(
        ImmutableList.of(4), ImmutableList.of(10), ImmutableList.of(12),
        ImmutableList.of(3))), app(app(Lists.map(), abs(cons(ref(1), primitive(Term
        .of())))), list));
  }

  public void testReduceEmptyList() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of());

    TestUtil.assertReducesToData(LInteger.of(1), app(app(app(Lists.reduce(),
        primitive(LSymbol.of("foo"))), list), primitive(LInteger.of(1))));
  }

  public void testReduceSumOneElement() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4));

    TestUtil.assertReducesToData(LInteger.of(5), app(app(app(Lists.reduce(),
        Integers.add()), list), primitive(LInteger.of(1))));
  }

  public void testReduceSumManyElements() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 5, 6, 7));

    TestUtil.assertReducesToData(LInteger.of(23), app(app(app(Lists.reduce(),
        Integers.add()), list), primitive(LInteger.of(1))));
  }

  public void testReduceNonCommutative() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 5, 6, 7));

    TestUtil.assertReducesToData(LInteger.of(4), app(app(app(Lists.reduce(),
        abs(abs(ref(2)))), list), primitive(LInteger.of(1))));

    TestUtil.assertReducesToData(LInteger.of(1), app(app(app(Lists.reduce(),
        abs(abs(ref(1)))), list), primitive(LInteger.of(1))));
  }

  public void testMemberEmptyList() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of());

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        primitive(LSymbol.of("foo"))));
  }

  public void testMemberOnlyElement() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo"));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        primitive(LSymbol.of("foo"))));
  }

  @SuppressWarnings("unchecked")
  public void testMemberShouldReturnTrue() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo", "bar", 5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        primitive(LSymbol.of("foo"))));
  }

  @SuppressWarnings("unchecked")
  public void testMemberNonemptyShouldReturnFalse() {
    Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo", "bar", 5));

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        primitive(LSymbol.of("baz"))));
  }

  public void testMemberShouldContainEmptyList() {
    Term list =
        Util.convertJavaObjectToTerm(ImmutableList.of("foo", ImmutableList.of(),
            5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        primitive(Term.of())));
  }

  public void testMemberShouldntFindInNested() {
    Term list =
        Util.convertJavaObjectToTerm(ImmutableList.of("foo", ImmutableList.of(3,
            4), 5));

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        primitive(LInteger.of(3))));
  }

  public void testMemberShouldContainNonEmptyList() {
    Term list =
        Util.convertJavaObjectToTerm(ImmutableList.of("foo", ImmutableList.of(3,
            4), 5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        Util.convertJavaObjectToTerm(ImmutableList.of(3, 4))));
  }
}
