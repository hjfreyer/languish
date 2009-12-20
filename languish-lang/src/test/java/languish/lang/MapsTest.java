package languish.lang;

import static languish.base.Terms.*;
import static languish.tools.testing.TestUtil.FOUR;
import junit.framework.TestCase;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.lib.Maps;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

public class MapsTest extends TestCase {

  public void testGetFromEmpty() {
    Term map = Term.NULL;

    Term actual =
        app(app(app(Maps.get(), map), Terms.primObj("key")), Terms.primObj(4));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(4), actual);

    actual =
        app(app(app(Maps.get(), map), Terms.primObj("key")), Terms.primObj(2));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(2), actual);
  }

  public void testGetFromEmptyWithNullKey() {
    Term map = Term.NULL;

    Term actual = app(app(app(Maps.get(), map), Term.NULL), Terms.primObj(4));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(4), actual);
  }

  public void testGetDefaultAbs() {
    Term func = abs(primObj(3));

    Term map = Term.NULL;

    Term actual =
        app(
            app(app(app(Maps.get(), map), Terms.primObj("foo")), func),
            Term.NULL);

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(3), actual);
  }

  public void testSingleElement() {
    Term map = Term.NULL;

    map = app(app(app(Maps.put(), map), //
        Terms.primitive(new Primitive("four"))), Terms.primitive(FOUR));

    Term actual =
        app(app(app(Maps.get(), map), Terms.primObj("four")), Terms.primObj(0));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(4), actual);

    actual =
        app(app(app(Maps.get(), map), Terms.primObj("five")), Terms.primObj(0));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(0), actual);
  }

  public void testTwoElements() {
    Term map = Term.NULL;

    map =
        app(app(app(Maps.put(), map), Terms.primObj("two")), Terms.primObj(2));

    map =
        app(app(app(Maps.put(), map), Terms.primObj(4)), Terms.primObj("four"));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf("four"), app(app(app(Maps
        .get(), map), Terms.primObj(4)), Terms.primObj(0)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(2), app(app(app(Maps
        .get(), map), Terms.primObj("two")), Terms.primObj(0)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(0), app(app(app(Maps
        .get(), map), Terms.primObj("five")), Terms.primObj(0)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(4), app(app(app(Maps
        .get(), map), Terms.primObj("five")), Terms.primObj(4)));
  }

  public void testOverwrite() {
    Term map = Term.NULL;

    map =
        app(app(app(Maps.put(), map), Terms.primObj("two")), Terms.primObj(2));

    map =
        app(app(app(Maps.put(), map), Terms.primObj("four")), Terms.primObj(4));

    map =
        app(app(app(Maps.put(), map), Terms.primObj("two")), Terms.primObj(3));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(3), app(app(app(Maps
        .get(), map), Terms.primObj("two")), Terms.primObj(0)));
  }

  public void testNullKey() {
    Term map = Term.NULL;

    map = app(app(app(Maps.put(), map), Term.NULL), Terms.primObj(2));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(2), app(app(app(Maps
        .get(), map), Term.NULL), Terms.primObj(0)));
  }

  public void testNullValue() {
    Term map = Term.NULL;

    map = app(app(app(Maps.put(), map), Terms.primObj("nil")), Term.NULL);

    TestUtil.assertReducesToData(PrimitiveTree.of(), app(app(app(
        Maps.get(),
        map), Terms.primObj("nil")), Terms.primObj(0)));
  }

  public void testValueAbs() {
    Term func = abs(primObj(3));

    Term map = Term.NULL;

    map = app(app(app(Maps.put(), map), Terms.primObj("func")), func);

    Term result =
        app(app(app(app(Maps.get(), map), Terms.primObj("func")), Terms
            .primObj(0)), Term.NULL);

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(3), result);
  }
}
