package languish.base;

import static languish.base.Term.NULL;
import static languish.base.Terms.*;
import static languish.tools.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.tools.testing.LanguishTestCase;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Tree;

public class ConsTest extends TestCase {
	public enum Tests implements LanguishTestCase {
		GET_NULL( //
				NULL,
				null,
				PrimitiveTree.from(ImmutableList.of())),

		CREATE_SINGLETON( //
				cons(primitive(FIVE), NULL),
				null,
				PrimitiveTree.from(ImmutableList.of(5))),

		CAR_SINGLETON( //
				car(cons(primitive(FIVE), NULL)),
				null,// app(cons( app(abs(abs(ref(2))), primitive(FIVE)), Term.NULL),
				PrimitiveTree.from(5)),

		CAR_PAIR( //
				car(cons(primitive(FOUR), cons(primitive(FIVE), NULL))),
				null,// app(cons( app(abs(abs(ref(2))), primitive(FIVE)), Term.NULL),
				PrimitiveTree.from(4)),

		CDR_PAIR( //
				cdr(cons(primitive(FOUR), cons(primitive(FIVE), NULL))),
				null,// app(cons( app(abs(abs(ref(2))), primitive(FIVE)), Term.NULL),
				PrimitiveTree.from(ImmutableList.of(5))),

		GET_PAIR_WITH_SINGLE_REF( //
				app(abs(cons(ref(1), cons(ref(1), NULL))), primitive(FOUR)),
				cons(primitive(FOUR), cons(primitive(FOUR), NULL)),
				PrimitiveTree.from(ImmutableList.of(4, 4))),

		GET_PAIR_WITH_DOUBLE_REF( //
				app(
						app(abs(abs(cons(ref(1), cons(ref(2), NULL)))), primitive(FOUR)),
						primitive(FIVE)),
				app(abs(cons(ref(1), cons(primitive(FOUR), NULL))), primitive(FIVE)),
				PrimitiveTree.from(ImmutableList.of(5, 4))),

		GET_NESTED_LIST( //
				cons(cons(primitive(FIVE), NULL), cons(primitive(FOUR), NULL)),
				null,
				PrimitiveTree.from(ImmutableList.of(ImmutableList.of(5), 4))),

		GET_LIST_WITH_UNREDUCED_CAR( //
				cons(app(IDENT, primitive(FIVE)), cons(primitive(FOUR), NULL)),
				null,
				PrimitiveTree.from(ImmutableList.of(5, 4))),

		GET_LIST_WITH_UNREDUCED_CDR( //
				cons(primitive(FIVE), app(IDENT, cons(primitive(FOUR), NULL))),
				null,
				PrimitiveTree.from(ImmutableList.of(5, 4))),

		;

		private final Term expression;
		private final Term reducedOnce;
		private final Tree<Primitive> reducedCompletely;

		private Tests(
				Term expression,
				Term reducedOnce,
				Tree<Primitive> reducedCompletely) {
			this.expression = expression;
			this.reducedOnce = reducedOnce;
			this.reducedCompletely = reducedCompletely;
		}

		public Term getExpression() {
			return expression;
		}

		public Term getReducedOnce() {
			return reducedOnce;
		}

		public Tree<Primitive> getReducedCompletely() {
			return reducedCompletely;
		}
	}

	public void test() {
		for (LanguishTestCase test : Tests.values()) {
			TestUtil.assertLanguishTestCase(test);
		}
	}
}
