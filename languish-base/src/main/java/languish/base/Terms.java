package languish.base;

import java.util.Arrays;
import java.util.List;

import languish.util.PrimitiveTree;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Terms {

	public static final Term TRUE = abs(abs(ref(2)));
	public static final Term FALSE = abs(abs(ref(1)));
	public static final Term NOT =
			abs(abs(abs(app(app(ref(3), ref(1)), ref(2)))));

	public static final Term CONS =
			abs(abs(abs(app(app(ref(1), ref(3)), ref(2)))));

	public static final Term CAR = abs(app(ref(1), TRUE));
	public static final Term CDR = abs(app(ref(1), FALSE));

	public static Term abs(Term exp) {
		return new Term(Operations.ABS, exp, Term.NULL);
	}

	public static Term app(Term func, Term arg) {
		return new Term(Operations.APP, func, arg);
	}

	public static Term primitive(Primitive prim) {
		return new Term(Operations.PRIMITIVE, prim, Term.NULL);
	}

	public static Term primObj(Object obj) {
		return new Term(Operations.PRIMITIVE, new Primitive(obj), Term.NULL);
	}

	public static Term ref(int i) {
		return new Term(Operations.REF, i, Term.NULL);
	}

	public static Term nativeApply(final NativeFunction func, Term arg) {
		return new Term(Operations.NATIVE_APPLY, Terms
				.primitive(new Primitive(func)), arg);
	}

	public static Term print(Term term) {
		return new Term(Operations.PRINT, term, Term.NULL);
	}

	public static Term cons(Term obj1, Term obj2) {
		return app(app(CONS, obj1), obj2);
	}

	public static Term car(Term obj) {
		return Terms.app(CAR, obj);
	}

	public static Term cdr(Term obj) {
		return Terms.app(CDR, obj);
	}

	public static Term equals(Term first, Term second) {
		return new Term(Operations.EQUALS, first, second);
	}

	public static Term branch(Term condition, Term thenTerm, Term elseTerm) {
		return Terms.app(Terms.app(condition, thenTerm), elseTerm);
	}

	private Terms() {
	}

	public static Term convertJavaObjectToTerm(Tree<Primitive> obj) {
		if (obj.isList()) {
			List<Tree<Primitive>> list = obj.asList();

			Term result = Term.NULL;
			for (int i = list.size() - 1; i >= 0; i--) {
				result = Terms.cons(Terms.convertJavaObjectToTerm(list.get(i)), result);
			}

			return result;
		} else if (obj.isLeaf()) {
			return Terms.primitive(obj.asLeaf());
		}

		throw new AssertionError();
	}

	public static Term convertListToTerm(Object... objs) {
		return convertJavaObjectToTerm(PrimitiveTree.from(Arrays.asList(objs)));
	}

	/**
	 * @deprecated Use {@link #convertTermToJavaObject(Term,int)} instead
	 */
	@Deprecated
	public static Tree<Primitive> convertTermToJavaObject(Term term) {
		return convertTermToJavaObject(term, 0);
	}

	public static Tree<Primitive> convertTermToJavaObject(Term term, int depth) {
		term = term.reduceCompletely();

		// System.out.println(depth);
		Operation op = term.getOperation();
		if (op == Operations.NOOP) {
			return Tree.<Primitive> empty();
		}

		if (op == Operations.PRIMITIVE) {
			return Tree.leaf(((Primitive) term.getFirst()));
		}

		if (op == Operations.ABS) {
			// System.out.println(term);
			Tree<Primitive> car =
					Terms.convertTermToJavaObject(Terms.car(term), depth + 1);
			Tree<Primitive> cdr =
					Terms.convertTermToJavaObject(Terms.cdr(term), depth + 1);

			List<Tree<Primitive>> result = Lists.newLinkedList();

			result.add(car);
			result.addAll(cdr.asList());

			return Tree.inode(ImmutableList.copyOf(result));
		}

		throw new IllegalArgumentException("term is not in a convertible state: "
				+ term);
	}

	@SuppressWarnings("unchecked")
	public static Tree<String> convertTermToAst(Term term) {
		if (term.getOperation() == Operations.NOOP) {
			return Tree.leaf("NULL");
		}

		if (term.getOperation() == Operations.REF) {
			return Tree.copyOfList("REF", term.getFirst().toString(), "NULL");
		}

		if (term.getOperation() == Operations.PRIMITIVE) {
			return Tree.copyOfList("PRIMITIVE", term.getFirst().toString(), "NULL");
		}

		Tree<String> op = Tree.leaf(term.getOperation().toString());
		Tree<String> first = convertTermToAst((Term) term.getFirst());
		Tree<String> second = convertTermToAst((Term) term.getSecond());

		return Tree.inode(op, first, second);
	}

	public static Term compileAstToTerm(Tree<String> ast) {
		if (ast.isLeaf() && ast.asLeaf().equals("NULL")) {
			return Term.NULL;
		}
		String opName = ast.asList().get(0).asLeaf();
		Operation op = Operations.fromName(opName);
		Tree<String> first = ast.asList().get(1);
		Tree<String> second = ast.asList().get(2);
		if (op == Operations.PRIMITIVE) {
			return Terms.primObj(first.asLeaf());
		} else if (op == Operations.REF) {
			return Terms.ref(Integer.parseInt(first.asLeaf()));
		}
		return new Term(op, Terms.compileAstToTerm(first), Terms
				.compileAstToTerm(second));
	}

	public static Term compileTermAstToTerm(Term astTerm) {
		Tree<Primitive> astPrim = Terms.convertTermToJavaObject(astTerm, 0);

		Tree<String> ast =
				Trees.transform(astPrim, new Function<Primitive, String>() {
					@Override
					public String apply(Primitive from) {
						return from.asString();
					}
				});
		return Terms.compileAstToTerm(ast);
	}
}
