package languish.base;

import static languish.base.Terms.*;
import static languish.base.testing.TestUtil.*;
import junit.framework.TestCase;
import languish.base.testing.LanguishTestCase;
import languish.base.testing.TestUtil;

import com.hjfreyer.util.Tree;

public class AbstractionTest extends TestCase {
	public enum Tests implements LanguishTestCase {
		BASIC_APPLY( //
				app(IDENT, primObj(5)),
				"[APP [ABS [REF 1 NULL] NULL] [PRIMITIVE 5 NULL]]",
				primObj(5),
				Tree.leaf(FIVE)),

		ARGUMENT_CHOOSER_1( //
				app(app(TRUE, primitive(FOUR)), primitive(FIVE)),
				"[APP [APP [ABS [ABS [REF 2 NULL] NULL] NULL] "
						+ "[PRIMITIVE 4 NULL]] [PRIMITIVE 5 NULL]]",
				app(abs(primitive(FOUR)), primitive(FIVE)),
				Tree.leaf(FOUR)),

		ARGUMENT_CHOOSER_2( //
				app(app(Terms.FALSE, primitive(TestUtil.FOUR)), primitive(FIVE)),
				"[APP [APP [ABS [ABS [REF 1 NULL] NULL] NULL] [PRIMITIVE 4 NULL]] "
						+ "[PRIMITIVE 5 NULL]]",
				app(abs(ref(1)), primitive(FIVE)),
				Tree.leaf(FIVE)),

		NON_HALTER( //
				TestUtil.LOOP,
				"[APP [ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL] "
						+ "[ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL]]",
				TestUtil.LOOP,
				null),

		IRRELEVANT_NON_HALTER( //
				app(app(Terms.TRUE, primitive(TestUtil.FOUR)), TestUtil.LOOP),
				"[APP [APP [ABS [ABS [REF 2 NULL] NULL] NULL] [PRIMITIVE 4 NULL]] "
						+ "[APP [ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL] "
						+ "[ABS [APP [REF 1 NULL] [REF 1 NULL]] NULL]]]",
				app(abs(primitive(FOUR)), TestUtil.LOOP),
				Tree.leaf(FOUR)),

		;

		private final Term expression;
		private final String code;
		private final Term reducedOnce;
		private final Tree<Primitive> reducedCompletely;

		private Tests(
				Term expression,
				String code,
				Term reducedOnce,
				Tree<Primitive> reducedCompletely) {
			this.expression = expression;
			this.code = code;
			this.reducedOnce = reducedOnce;
			this.reducedCompletely = reducedCompletely;
		}

		@Override
		public Term getExpression() {
			return expression;
		}

		public String getCode() {
			return code;
		}

		@Override
		public Term getReducedOnce() {
			return reducedOnce;
		}

		@Override
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