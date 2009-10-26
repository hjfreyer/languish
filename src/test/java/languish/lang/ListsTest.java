package languish.lang;

import static languish.base.Lambda.*;
import junit.framework.TestCase;
import languish.base.Tuple;
import languish.base.Util;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.testing.TestUtil;

import com.google.common.collect.ImmutableList;

public class ListsTest extends TestCase {

  private static final Tuple ADD_ONE =
      abs(prim(DataFunctions.ADD, cons(data(LInteger.of(1)), ref(1))));

  public void testMapEmpty() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of());

    TestUtil.assertReducesToData(Tuple.of(), app(app(Lists.map(), ADD_ONE),
        list));
  }

  public void testMapIntegers() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of(4, 10, 12, 3));

    TestUtil.assertReducesTo(Util.convertJavaToPrimitive(ImmutableList.of(5,
        11, 13, 4)), app(app(Lists.map(), ADD_ONE), list));
  }

  @SuppressWarnings("unchecked")
  public void testMapSingletonWrapper() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of(4, 10, 12, 3));

    TestUtil.assertReducesTo(Util.convertJavaToPrimitive(ImmutableList.of(
        ImmutableList.of(4), ImmutableList.of(10), ImmutableList.of(12),
        ImmutableList.of(3))), app(app(Lists.map(), abs(cons(ref(1), data(Tuple
        .of())))), list));
  }

  public void testReduceEmptyList() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of());

    TestUtil.assertReducesToData(LInteger.of(1), app(app(app(Lists.reduce(),
        data(LSymbol.of("foo"))), list), data(LInteger.of(1))));
  }

  public void testReduceSumOneElement() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of(4));

    TestUtil.assertReducesToData(LInteger.of(5), app(app(app(Lists.reduce(),
        Integers.add()), list), data(LInteger.of(1))));
  }

  public void testReduceSumManyElements() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of(4, 5, 6, 7));

    TestUtil.assertReducesToData(LInteger.of(23), app(app(app(Lists.reduce(),
        Integers.add()), list), data(LInteger.of(1))));
  }

  public void testReduceNonCommutative() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of(4, 5, 6, 7));

    TestUtil.assertReducesToData(LInteger.of(4), app(app(app(Lists.reduce(),
        abs(abs(ref(2)))), list), data(LInteger.of(1))));

    TestUtil.assertReducesToData(LInteger.of(1), app(app(app(Lists.reduce(),
        abs(abs(ref(1)))), list), data(LInteger.of(1))));
  }

  public void testMemberEmptyList() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of());

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        data(LSymbol.of("foo"))));
  }

  public void testMemberOnlyElement() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of("foo"));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        data(LSymbol.of("foo"))));
  }

  @SuppressWarnings("unchecked")
  public void testMemberShouldReturnTrue() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of("foo", "bar", 5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        data(LSymbol.of("foo"))));
  }

  @SuppressWarnings("unchecked")
  public void testMemberNonemptyShouldReturnFalse() {
    Tuple list = Util.convertJavaToPrimitive(ImmutableList.of("foo", "bar", 5));

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        data(LSymbol.of("baz"))));
  }

  public void testMemberShouldContainEmptyList() {
    Tuple list =
        Util.convertJavaToPrimitive(ImmutableList.of("foo", ImmutableList.of(),
            5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        data(Tuple.of())));
  }

  public void testMemberShouldntFindInNested() {
    Tuple list =
        Util.convertJavaToPrimitive(ImmutableList.of("foo", ImmutableList.of(3,
            4), 5));

    TestUtil.assertReducesToData(LBoolean.FALSE, app(app(Lists.member(), list),
        data(LInteger.of(3))));
  }

  public void testMemberShouldContainNonEmptyList() {
    Tuple list =
        Util.convertJavaToPrimitive(ImmutableList.of("foo", ImmutableList.of(3,
            4), 5));

    TestUtil.assertReducesToData(LBoolean.TRUE, app(app(Lists.member(), list),
        Util.convertJavaToPrimitive(ImmutableList.of(3, 4))));
  }
}
