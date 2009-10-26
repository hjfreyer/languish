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

public class VisitorTest extends TestCase {

  private static final Tuple ADD_FORTYTWO =
      abs(prim(DataFunctions.ADD, cons(data(LInteger.of(42)), ref(1))));

  private static final Tuple ADD_FORTYTWO_TO_CAR =
      abs(prim(DataFunctions.ADD, cons(data(LInteger.of(42)), car(ref(1)))));

  private static final Tuple SUM_UP =
      abs(app(app(app(Lists.reduce(), Integers.add()), ref(1)), data(LInteger
          .of(0))));

  private static final Tuple PROD_UP =
      abs(app(app(app(Lists.reduce(), Integers.multiply()), ref(1)),
          data(LInteger.of(1))));

  @SuppressWarnings("unchecked")
  public void testLeaf() {
    Tuple functionMap =
        Util.convertJavaToPrimitive(ImmutableList.of(ImmutableList.of("func1",
            ADD_FORTYTWO)));
    Tuple isLeaf = abs(data(LBoolean.TRUE));
    Tuple tree = Util.convertJavaToPrimitive(ImmutableList.of("func1", 3));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(45), visitorCall);
  }

  @SuppressWarnings("unchecked")
  public void testSimpleTree() {
    Tuple functionMap = Util.convertJavaToPrimitive(ImmutableList.of( //
        ImmutableList.of("additup", SUM_UP), //
        ImmutableList.of("num", abs(ref(1)))));
    Tuple isLeaf =
        abs(app(app(Data.equals(), data(LSymbol.of("num"))), ref(1)));
    Tuple tree =
        Util.convertJavaToPrimitive(ImmutableList.of("additup", //
            ImmutableList.of(
                ImmutableList.of("num", 3), //
                ImmutableList.of("num", 4), ImmutableList.of("num", 12),
                ImmutableList.of("num", -2))));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(17), visitorCall);
  }

  @SuppressWarnings("unchecked")
  public void testMultipleLeaves() {
    Tuple functionMap = Util.convertJavaToPrimitive(ImmutableList.of( //
        ImmutableList.of("additup", SUM_UP), //
        ImmutableList.of("num", abs(ref(1))), //
        ImmutableList.of("add_42", ADD_FORTYTWO)));
    Tuple isLeaf =
        abs(app(app(Booleans.or(), app(app(Data.equals(), data(LSymbol
            .of("num"))), ref(1))), app(app(Data.equals(), data(LSymbol
            .of("add_42"))), ref(1))));
    Tuple tree =
        Util.convertJavaToPrimitive(ImmutableList.of("additup", //
            ImmutableList.of(
                ImmutableList.of("add_42", 3), //
                ImmutableList.of("num", 4), ImmutableList.of("num", 12),
                ImmutableList.of("num", -2))));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(59), visitorCall);

  }

  @SuppressWarnings("unchecked")
  public void testMultipleTiers() {
    Tuple functionMap = Util.convertJavaToPrimitive(ImmutableList.of( //
        ImmutableList.of("additup", SUM_UP), //
        ImmutableList.of("multitup", PROD_UP), //
        ImmutableList.of("num", abs(ref(1))), //
        ImmutableList.of("add_42_to_car", ADD_FORTYTWO_TO_CAR)));
    Tuple isLeaf =
        abs(app(app(Data.equals(), data(LSymbol.of("num"))), ref(1)));

    Tuple branch1 = Util.convertJavaToPrimitive(ImmutableList.of("additup", //
        ImmutableList.of(ImmutableList.of("num", 4), //
            ImmutableList.of("num", 12))));

    Tuple branch2 = Util.convertJavaToPrimitive(ImmutableList.of("num", 4));

    Tuple branch3 =
        Util.convertJavaToPrimitive(ImmutableList.of("add_42_to_car", //
            ImmutableList.of(ImmutableList.of("num", 4))));

    Tuple tree = Util.convertJavaToPrimitive(ImmutableList.of("multitup", //
        ImmutableList.of(branch1, branch2, branch3)));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(2944), visitorCall);
  }
}
