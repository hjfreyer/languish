package languish.lang;

import static languish.base.Terms.*;
import static languish.base.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.base.testing.TestUtil;
import languish.lib.Integers;
import languish.lib.Lists;
import languish.lib.MathFunctions;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Tree;

public class ListsTest extends TestCase {

	private static final Term SUM_REDUCE = app(Lists.reduce(), Integers.add());

	private static final Term ADD_ONE =
			abs(nativeApply(MathFunctions.ADD, cons(
					primitive(new Primitive(1)),
					cons(ref(1), Term.NULL))));

	public void testMapEmpty() {
		Term list = Term.NULL;

		TestUtil.assertReducesToData(Tree.<Primitive> empty(), app(app(
				Lists.map(),
				ADD_ONE), list));
	}

	public void testAddOne() {
		TestUtil.assertReducesToData(Tree.leaf(TestUtil.FIVE), app(
				ADD_ONE,
				primitive(TestUtil.FOUR)));
	}

	public void testSingleMap() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(4)));

		TestUtil.assertReducesToData(PrimitiveTree.from(ImmutableList.of(5)), app(
				app(Lists.map(), ADD_ONE),
				list));
	}

	public void testMapIntegers() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						4,
						10,
						12,
						3)));

		TestUtil.assertReducesToData(PrimitiveTree.from(ImmutableList.of(
				5,
				11,
				13,
				4)), app(app(Lists.map(), ADD_ONE), list));
	}

	@SuppressWarnings("unchecked")
	public void testMapSingletonWrapper() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						4,
						10,
						12,
						3)));

		TestUtil.assertReducesToData(PrimitiveTree.from(ImmutableList.of(
				ImmutableList.of(4),
				ImmutableList.of(10),
				ImmutableList.of(12),
				ImmutableList.of(3))), app(app(
				Lists.map(),
				abs(cons(ref(1), Term.NULL))), list));
	}

	public void testReduceEmptyList() {
		Term list = Term.NULL;

		TestUtil.assertReducesToData(PrimitiveTree.from(1), app(app(app(Lists
				.reduce(), Term.NULL), list), primitive(TestUtil.ONE)));
	}

	public void testReduceSumOneElement() {
		Term list = Terms.convertJavaObjectToTerm( //
				PrimitiveTree.from(ImmutableList.of(4)));

		TestUtil.assertReducesToData(Tree.leaf(FIVE), app(
				app(SUM_REDUCE, list),
				primitive(ONE)));
	}

	public void testReduceSumManyElements() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						4,
						5,
						6,
						7)));

		TestUtil.assertReducesToData(PrimitiveTree.from(23), app(app(
				SUM_REDUCE,
				list), primitive(ONE)));
	}

	public void testReduceNonCommutative() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						4,
						5,
						6,
						7)));

		TestUtil.assertReducesToData(PrimitiveTree.from(4), app(app(app(Lists
				.reduce(), abs(abs(ref(2)))), list), primitive(ONE)));

		TestUtil.assertReducesToData(PrimitiveTree.from(1), app(app(app(Lists
				.reduce(), abs(abs(ref(1)))), list), primitive(ONE)));
	}

	public void testMemberEmptyList() {
		Term list = Term.NULL;

		TestUtil.assertReducesToFalse(app(
				app(Lists.member(), list),
				primitive(new Primitive("foo"))));
	}

	public void testMemberOnlyElement() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList
						.of("foo")));

		TestUtil.assertReducesToTrue(app(
				app(Lists.member(), list),
				primitive(new Primitive("foo"))));
	}

	@SuppressWarnings("unchecked")
	public void testMemberShouldReturnTrue() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						"foo",
						"bar",
						5)));

		TestUtil.assertReducesToTrue(app(
				app(Lists.member(), list),
				primitive(new Primitive("foo"))));
	}

	@SuppressWarnings("unchecked")
	public void testMemberNonemptyShouldReturnFalse() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						"foo",
						"bar",
						5)));

		TestUtil.assertReducesToFalse(app(
				app(Lists.member(), list),
				primitive(new Primitive("baz"))));
	}

	public void testMemberShouldContainEmptyList() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						"foo",
						ImmutableList.of(),
						5)));

		TestUtil.assertReducesToTrue(app(app(Lists.member(), list), Term.NULL));
	}

	public void testMemberShouldntFindInNested() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						"foo",
						ImmutableList.of(3, 4),
						5)));

		TestUtil.assertReducesToFalse(app(
				app(Lists.member(), list),
				primitive(THREE)));
	}

	public void testMemberShouldContainNonEmptyList() {
		Term list =
				Terms.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(
						"foo",
						ImmutableList.of(3, 4),
						5)));

		TestUtil.assertReducesToTrue(app(app(Lists.member(), list), Terms
				.convertJavaObjectToTerm(PrimitiveTree.from(ImmutableList.of(3, 4)))));
	}
}
