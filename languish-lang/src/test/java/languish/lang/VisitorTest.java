package languish.lang;

import static languish.base.Terms.*;
import junit.framework.TestCase;
import languish.base.Term;
import languish.base.Terms;
import languish.lib.Integers;
import languish.lib.Lists;
import languish.lib.Maps;
import languish.lib.Visitor;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;

public class VisitorTest extends TestCase {

  private static final Term ADD_FORTYTWO =
      abs(app(app(Integers.add(), primObj(42)), ref(1)));

  private static final Term SUM_UP =
      abs(app(app(app(Lists.reduce(), Integers.add()), ref(1)), primObj(0)));

  private static final Term PROD_UP =
      abs(app(app(app(Lists.reduce(), Integers.multiply()), ref(1)), primObj(1)));

  @SuppressWarnings("unchecked")
  public void testLeaf() {
    Term functionMap =
        app(app(app(Maps.put(), Term.NULL), primObj("func1")), ADD_FORTYTWO);

    Term isLeaf = abs(primObj(true));
    Term tree =
        Terms.convertJavaObjectToTerm(PrimitiveTree.copyOf(ImmutableList.of(
            "func1",
            3)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(45), app(app(app(Visitor
        .visitTree(), functionMap), isLeaf), tree));

    tree =
        Terms.convertJavaObjectToTerm(PrimitiveTree.copyOf(ImmutableList.of(
            "func1",
            -3)));

    TestUtil.assertReducesToData(PrimitiveTree.copyOf(39), app(app(app(Visitor
        .visitTree(), functionMap), isLeaf), tree));
  }

  // @SuppressWarnings("unchecked")
  // public void testSimpleTree() {
  // Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
  // ImmutableList.of("additup", SUM_UP), //
  // ImmutableList.of("num", abs(ref(1)))));
  // Term isLeaf =
  // abs(app(app(Data.equals(), primitive(LSymbol.of("num"))), ref(1)));
  // Term tree =
  // Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
  // ImmutableList.of(
  // ImmutableList.of("num", 3), //
  // ImmutableList.of("num", 4), ImmutableList.of("num", 12),
  // ImmutableList.of("num", -2))));
  //
  // Term visitorCall =
  // app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
  //
  // TestUtil.assertReducesToData(LInteger.of(17), visitorCall);
  // }
  //
  // @SuppressWarnings("unchecked")
  // public void testMultipleLeaves() {
  // Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
  // ImmutableList.of("additup", SUM_UP), //
  // ImmutableList.of("num", abs(ref(1))), //
  // ImmutableList.of("add_42", ADD_FORTYTWO)));
  // Term isLeaf =
  // abs(app(app(Booleans.or(), app(app(Data.equals(), primitive(LSymbol
  // .of("num"))), ref(1))), app(app(Data.equals(), primitive(LSymbol
  // .of("add_42"))), ref(1))));
  // Term tree =
  // Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
  // ImmutableList.of(
  // ImmutableList.of("add_42", 3), //
  // ImmutableList.of("num", 4), ImmutableList.of("num", 12),
  // ImmutableList.of("num", -2))));
  //
  // Term visitorCall =
  // app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
  //
  // TestUtil.assertReducesToData(LInteger.of(59), visitorCall);
  //
  // }
  //
  // @SuppressWarnings("unchecked")
  // public void testMultipleTiers() {
  // Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
  // ImmutableList.of("additup", SUM_UP), //
  // ImmutableList.of("multitup", PROD_UP), //
  // ImmutableList.of("num", abs(ref(1))), //
  // ImmutableList.of("add_42_to_car", ADD_FORTYTWO_TO_CAR)));
  // Term isLeaf =
  // abs(app(app(Data.equals(), primitive(LSymbol.of("num"))), ref(1)));
  //
  // Term branch1 = Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
  // ImmutableList.of(ImmutableList.of("num", 4), //
  // ImmutableList.of("num", 12))));
  //
  // Term branch2 = Util.convertJavaObjectToTerm(ImmutableList.of("num", 4));
  //
  // Term branch3 =
  // Util.convertJavaObjectToTerm(ImmutableList.of("add_42_to_car", //
  // ImmutableList.of(ImmutableList.of("num", 4))));
  //
  // Term tree = Util.convertJavaObjectToTerm(ImmutableList.of("multitup", //
  // ImmutableList.of(branch1, branch2, branch3)));
  //
  // Term visitorCall =
  // app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
  //
  // TestUtil.assertReducesToData(LInteger.of(2944), visitorCall);
  // }
}
