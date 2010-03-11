package languish.base;

public class Terms {

	public static final Term NULL = primitive(Primitives.NULL);

	public static final Term TRUE = abs(abs(ref(2)));
	public static final Term FALSE = abs(abs(ref(1)));
	public static final Term NOT =
			abs(abs(abs(app(app(ref(3), ref(1)), ref(2)))));

	public static final Term CONS =
			abs(abs(abs(app(app(ref(1), ref(3)), ref(2)))));

	public static final Term CAR = abs(app(ref(1), TRUE));
	public static final Term CDR = abs(app(ref(1), FALSE));

	public static Term abs(Term exp) {
		return new Term(Operation.ABS, exp, NULL);
	}

	public static Term app(Term func, Term arg) {
		return new Term(Operation.APP, func, arg);
	}

	public static Term primitive(Primitive prim) {
		return new Term(Operation.PRIMITIVE, prim, NULL);
	}

	public static Term isPrimitive(Term term) {
		return new Term(Operation.IS_PRIMITIVE, term, NULL);
	}

	public static Term primObj(Object obj) {
		return new Term(Operation.PRIMITIVE, new Primitive(obj), NULL);
	}

	public static Term ref(int i) {
		return new Term(Operation.REF, i, NULL);
	}

	public static Term nativeApply(String funcName, Term arg) {
		return new Term(Operation.NATIVE_APPLY, funcName, arg);
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

	public static Term branch(Term condition, Term thenTerm, Term elseTerm) {
		return Terms.app(Terms.app(condition, thenTerm), elseTerm);
	}

	private Terms() {
	}
}
