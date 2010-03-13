package languish.interpreter;

import java.util.Map;

import languish.base.NativeFunction;
import languish.lib.GrammarFunctions;
import languish.lib.MathFunctions;
import languish.lib.ParsersFunctions;
import languish.lib.bootstrap.lambdaplus.LambdaPlusFunctions;

import com.google.common.collect.ImmutableMap;

public class StandardLib {

	public static final Map<String, NativeFunction> NATIVE_FUNCTIONS =
			ImmutableMap
					.<String, NativeFunction> builder()
					.putAll(MathFunctions.FUNCTION_MAP)
					.putAll(ParsersFunctions.FUNCTION_MAP)
					.putAll(GrammarFunctions.FUNCTION_MAP)
					.putAll(LambdaPlusFunctions.FUNCTION_MAP)
					.build();

	private StandardLib() {
	}
}
