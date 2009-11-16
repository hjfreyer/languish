//package languish.lang;
//
//import static languish.util.Lambda.*;
//import junit.framework.TestCase;
//import languish.lambda.Term;
//import languish.primitives.DataFunctions;
//import languish.primitives.LBoolean;
//import languish.primitives.LInteger;
//import languish.primitives.LSymbol;
//import languish.testing.TestUtil;
//import languish.util.Util;
//
//import com.google.common.collect.ImmutableList;
//
//public class VisitorTest extends TestCase {
//
//  private static final Term ADD_FORTYTWO =
//      abs(nativeApply(DataFunctions.ADD, cons(primitive(LInteger.of(42)), ref(1))));
//
//  private static final Term ADD_FORTYTWO_TO_CAR =
//      abs(nativeApply(DataFunctions.ADD, cons(primitive(LInteger.of(42)), car(ref(1)))));
//
//  private static final Term SUM_UP =
//      abs(app(app(app(Lists.reduce(), Integers.add()), ref(1)), primitive(LInteger
//          .of(0))));
//
//  private static final Term PROD_UP =
//      abs(app(app(app(Lists.reduce(), Integers.multiply()), ref(1)),
//          primitive(LInteger.of(1))));
//
//  @SuppressWarnings("unchecked")
//  public void testLeaf() {
//    Term functionMap =
//        Util.convertJavaObjectToTerm(ImmutableList.of(ImmutableList.of("func1",
//            ADD_FORTYTWO)));
//    Term isLeaf = abs(primitive(LBoolean.TRUE));
//    Term tree = Util.convertJavaObjectToTerm(ImmutableList.of("func1", 3));
//
//    Term visitorCall =
//        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
//
//    TestUtil.assertReducesToData(LInteger.of(45), visitorCall);
//  }
//
//  @SuppressWarnings("unchecked")
//  public void testSimpleTree() {
//    Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
//        ImmutableList.of("additup", SUM_UP), //
//        ImmutableList.of("num", abs(ref(1)))));
//    Term isLeaf =
//        abs(app(app(Data.equals(), primitive(LSymbol.of("num"))), ref(1)));
//    Term tree =
//        Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
//            ImmutableList.of(
//                ImmutableList.of("num", 3), //
//                ImmutableList.of("num", 4), ImmutableList.of("num", 12),
//                ImmutableList.of("num", -2))));
//
//    Term visitorCall =
//        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
//
//    TestUtil.assertReducesToData(LInteger.of(17), visitorCall);
//  }
//
//  @SuppressWarnings("unchecked")
//  public void testMultipleLeaves() {
//    Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
//        ImmutableList.of("additup", SUM_UP), //
//        ImmutableList.of("num", abs(ref(1))), //
//        ImmutableList.of("add_42", ADD_FORTYTWO)));
//    Term isLeaf =
//        abs(app(app(Booleans.or(), app(app(Data.equals(), primitive(LSymbol
//            .of("num"))), ref(1))), app(app(Data.equals(), primitive(LSymbol
//            .of("add_42"))), ref(1))));
//    Term tree =
//        Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
//            ImmutableList.of(
//                ImmutableList.of("add_42", 3), //
//                ImmutableList.of("num", 4), ImmutableList.of("num", 12),
//                ImmutableList.of("num", -2))));
//
//    Term visitorCall =
//        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
//
//    TestUtil.assertReducesToData(LInteger.of(59), visitorCall);
//
//  }
//
//  @SuppressWarnings("unchecked")
//  public void testMultipleTiers() {
//    Term functionMap = Util.convertJavaObjectToTerm(ImmutableList.of( //
//        ImmutableList.of("additup", SUM_UP), //
//        ImmutableList.of("multitup", PROD_UP), //
//        ImmutableList.of("num", abs(ref(1))), //
//        ImmutableList.of("add_42_to_car", ADD_FORTYTWO_TO_CAR)));
//    Term isLeaf =
//        abs(app(app(Data.equals(), primitive(LSymbol.of("num"))), ref(1)));
//
//    Term branch1 = Util.convertJavaObjectToTerm(ImmutableList.of("additup", //
//        ImmutableList.of(ImmutableList.of("num", 4), //
//            ImmutableList.of("num", 12))));
//
//    Term branch2 = Util.convertJavaObjectToTerm(ImmutableList.of("num", 4));
//
//    Term branch3 =
//        Util.convertJavaObjectToTerm(ImmutableList.of("add_42_to_car", //
//            ImmutableList.of(ImmutableList.of("num", 4))));
//
//    Term tree = Util.convertJavaObjectToTerm(ImmutableList.of("multitup", //
//        ImmutableList.of(branch1, branch2, branch3)));
//
//    Term visitorCall =
//        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
//
//    TestUtil.assertReducesToData(LInteger.of(2944), visitorCall);
//  }
// }
