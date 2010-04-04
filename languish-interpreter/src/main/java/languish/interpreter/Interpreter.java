package languish.interpreter;

import languish.base.Primitive;
import languish.base.Reducer;
import languish.base.Term;

import com.hjfreyer.util.Tree;

public class Interpreter {
	private static final Reducer STANDARD_REDUCER =
			new Reducer(StandardLib.NATIVE_FUNCTIONS);

	public static Tree<Primitive> convertTermToJavaObject(Term term) {
		return STANDARD_REDUCER.convertTermToJavaObject(term);
	}

	public static Term reduceCompletely(Term term) {
		return STANDARD_REDUCER.reduceCompletely(term);
	}

}
