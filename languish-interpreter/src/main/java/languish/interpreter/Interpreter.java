package languish.interpreter;

import languish.base.Reducer;

public class Interpreter {
	private static final Reducer STANDARD_REDUCER =
			new Reducer(StandardLib.NATIVE_FUNCTIONS);

}
