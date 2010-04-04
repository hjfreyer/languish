package languish.lang;

import junit.framework.TestCase;

public class VisitorTest extends TestCase {

	public void test() {
	}
	//
	// private static final Term ADD_FORTYTWO =
	// abs(app(app(Integers.add(), primObj(42)), ref(1)));
	//
	// private static final Term ADD_FORTYTWO_TO_CAR =
	// abs(app(ADD_FORTYTWO, car(ref(1))));
	//
	// private static final Term SUM_UP =
	// abs(app(app(app(Lists.reduce(), Integers.add()), ref(1)), primObj(0)));
	//
	// private static final Term PROD_UP =
	// abs(app(app(app(Lists.reduce(), Integers.multiply()), ref(1)),
	// primObj(1)));
	//
	// @SuppressWarnings("unchecked")
	// public void testLeaf() {
	// Term functionMap =
	// app(app(app(Maps.put(), Term.NULL), primObj("func1")), ADD_FORTYTWO);
	//
	// Term isLeaf = abs(Terms.TRUE);
	// Term tree =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "func1",
	// 3)));
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(45), app(app(app(Visitor
	// .visitTree(), functionMap), isLeaf), tree));
	//
	// tree =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "func1",
	// -3)));
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(39), app(app(app(Visitor
	// .visitTree(), functionMap), isLeaf), tree));
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void testSimpleTree() {
	// Term func1 = cons(primObj("additup"), cons(SUM_UP, Term.NULL));
	// Term func2 = cons(primObj("num"), cons(abs(ref(1)), Term.NULL));
	//
	// Term functionMap = cons(func1, cons(func2, Term.NULL));
	// Term isLeaf = abs(Terms.equals(Terms.primObj("num"), ref(1)));
	// Term tree =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "additup", //
	// ImmutableList.of(ImmutableList.of("num", 3), //
	// ImmutableList.of("num", 4),
	// ImmutableList.of("num", 12),
	// ImmutableList.of("num", -2)))));
	//
	// Term visitorCall =
	// app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(17), visitorCall);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void testSimpleTree2() {
	// Term func1 = cons(primObj("proditup"), cons(PROD_UP, Term.NULL));
	// Term func2 = cons(primObj("num"), cons(abs(ref(1)), Term.NULL));
	//
	// Term functionMap = cons(func1, cons(func2, Term.NULL));
	// Term isLeaf = abs(Terms.equals(Terms.primObj("num"), ref(1)));
	// Term tree =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "proditup", //
	// ImmutableList.of(ImmutableList.of("num", 3), //
	// ImmutableList.of("num", 4),
	// ImmutableList.of("num", 12),
	// ImmutableList.of("num", -2)))));
	//
	// Term visitorCall =
	// app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(-288), visitorCall);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void testMultipleLeaves() {
	// Term func1 = cons(primObj("additup"), cons(SUM_UP, Term.NULL));
	// Term func2 = cons(primObj("num"), cons(abs(ref(1)), Term.NULL));
	// Term func3 = cons(primObj("add_42"), cons(ADD_FORTYTWO, Term.NULL));
	//
	// Term functionMap = cons(func1, cons(func2, cons(func3, Term.NULL)));
	//
	// Term isLeaf =
	// abs(app(Terms.NOT, Terms.equals(Terms.primObj("additup"), ref(1))));
	//
	// Term tree =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "additup",
	// ImmutableList.of(ImmutableList.of("add_42", 3), //
	// ImmutableList.of("num", 4),
	// ImmutableList.of("num", 12),
	// ImmutableList.of("num", -2)))));
	//
	// Term visitorCall =
	// app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(59), visitorCall);
	//
	// }
	//
	// @SuppressWarnings("unchecked")
	// public void testMultipleTiers() {
	// Term func1 = cons(primObj("additup"), cons(SUM_UP, Term.NULL));
	// Term func2 = cons(primObj("proditup"), cons(PROD_UP, Term.NULL));
	// Term func3 = cons(primObj("num"), cons(abs(ref(1)), Term.NULL));
	// Term func4 =
	// cons(primObj("add_42_to_car"), cons(ADD_FORTYTWO_TO_CAR, Term.NULL));
	//
	// Term functionMap =
	// cons(func1, cons(func2, cons(func3, cons(func4, Term.NULL))));
	//
	// Term isLeaf = abs(Terms.equals(Terms.primObj("num"), ref(1)));
	//
	// Term branch1 =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "additup", //
	// ImmutableList.of(ImmutableList.of("num", 4), //
	// ImmutableList.of("num", 12)))));
	//
	// Term branch2 =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "num",
	// 4)));
	//
	// Term branch3 =
	// Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
	// "add_42_to_car", //
	// ImmutableList.of(ImmutableList.of("num", 4)))));
	//
	// Term fork = cons(branch1, cons(branch2, cons(branch3, Term.NULL)));
	//
	// Term tree = cons(primObj("proditup"), cons(fork, Term.NULL));
	//
	// Term visitorCall =
	// app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);
	//
	// TestUtil.assertReducesToData(PrimitiveTree.from(2944), visitorCall);
	// }
}
