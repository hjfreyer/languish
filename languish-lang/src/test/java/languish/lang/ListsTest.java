package languish.lang;

import static languish.base.Terms.*;
import junit.framework.TestCase;
import languish.api.MathFunctions;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.lib.Lists;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;

public class ListsTest extends TestCase {

  private static final Term ADD_ONE =
      abs(nativeApply(MathFunctions.ADD, cons(
          primitive(new Primitive(1)),
          ref(1))));

  public void testMapEmpty() {
    Term list = Terms.convertJavaObjectToTerm(PrimitiveTree.of());

    TestUtil.assertReducesToData(PrimitiveTree.of(), app(app(
        Lists.map(),
        ADD_ONE), list));
  }

  public void testMapIntegers() {
    Term list =
        Terms.convertJavaObjectToTerm(PrimitiveTree.copyOf(ImmutableList.of(
            4,
            10,
            12,
            3)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(ImmutableList.of(
        5,
        11,
        13,
        4)), app(app(Lists.map(), ADD_ONE), list));
  }

  @SuppressWarnings("unchecked")
  public void testMapSingletonWrapper() {
    Term list =
        Terms.convertJavaObjectToTerm(PrimitiveTree.copyOf(ImmutableList.of(
            4,
            10,
            12,
            3)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(ImmutableList.of(
        ImmutableList.of(4),
        ImmutableList.of(10),
        ImmutableList.of(12),
        ImmutableList.of(3))), app(app(
        Lists.map(),
        abs(cons(ref(1), Term.NULL))), list));
  }
  //
  // public void testReduceEmptyList() {
  // Term list = Util.convertJavaObjectToTerm(PrimitiveTree.of());
  //
  // TestUtil.assertReducesToData(LInteger.of(1), app(app(app(
  // Lists.reduce(),
  // primitive(LSymbol.of("foo"))), list), primitive(LInteger.of(1))));
  // }
  //
  // public void testReduceSumOneElement() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4));
  //
  // TestUtil.assertReducesToData(LInteger.of(5), app(app(app(
  // Lists.reduce(),
  // Integers.add()), list), primitive(LInteger.of(1))));
  // }
  //
  // public void testReduceSumManyElements() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 5, 6, 7));
  //
  // TestUtil.assertReducesToData(LInteger.of(23), app(app(app(
  // Lists.reduce(),
  // Integers.add()), list), primitive(LInteger.of(1))));
  // }
  //
  // public void testReduceNonCommutative() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of(4, 5, 6, 7));
  //
  // TestUtil.assertReducesToData(LInteger.of(4), app(app(app(
  // Lists.reduce(),
  // abs(abs(ref(2)))), list), primitive(LInteger.of(1))));
  //
  // TestUtil.assertReducesToData(LInteger.of(1), app(app(app(
  // Lists.reduce(),
  // abs(abs(ref(1)))), list), primitive(LInteger.of(1))));
  // }
  //
  // public void testMemberEmptyList() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of());
  //
  // TestUtil.assertReducesToData(LBoolean.FALSE, app(
  // app(Lists.member(), list),
  // primitive(LSymbol.of("foo"))));
  // }
  //
  // public void testMemberOnlyElement() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo"));
  //
  // TestUtil.assertReducesToData(LBoolean.TRUE, app(
  // app(Lists.member(), list),
  // primitive(LSymbol.of("foo"))));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public void testMemberShouldReturnTrue() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo", "bar",
  // 5));
  //
  // TestUtil.assertReducesToData(LBoolean.TRUE, app(
  // app(Lists.member(), list),
  // primitive(LSymbol.of("foo"))));
  // }
  //
  // @SuppressWarnings("unchecked")
  // public void testMemberNonemptyShouldReturnFalse() {
  // Term list = Util.convertJavaObjectToTerm(ImmutableList.of("foo", "bar",
  // 5));
  //
  // TestUtil.assertReducesToData(LBoolean.FALSE, app(
  // app(Lists.member(), list),
  // primitive(LSymbol.of("baz"))));
  // }
  //
  // public void testMemberShouldContainEmptyList() {
  // Term list =
  // Util.convertJavaObjectToTerm(ImmutableList.of(
  // "foo",
  // ImmutableList.of(),
  // 5));
  //
  // TestUtil.assertReducesToData(LBoolean.TRUE, app(
  // app(Lists.member(), list),
  // primitive(Term.of())));
  // }
  //
  // public void testMemberShouldntFindInNested() {
  // Term list =
  // Util.convertJavaObjectToTerm(ImmutableList.of("foo", ImmutableList.of(
  // 3,
  // 4), 5));
  //
  // TestUtil.assertReducesToData(LBoolean.FALSE, app(
  // app(Lists.member(), list),
  // primitive(LInteger.of(3))));
  // }
  //
  // public void testMemberShouldContainNonEmptyList() {
  // Term list =
  // Util.convertJavaObjectToTerm(ImmutableList.of("foo", ImmutableList.of(
  // 3,
  // 4), 5));
  //
  // TestUtil.assertReducesToData(LBoolean.TRUE, app(
  // app(Lists.member(), list),
  // Util.convertJavaObjectToTerm(ImmutableList.of(3, 4))));
  // }
}
