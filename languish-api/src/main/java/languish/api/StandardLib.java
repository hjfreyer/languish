package languish.api;

import java.util.Map;

import languish.api.bootstrap.PreprocessorFunctions;
import languish.base.NativeFunction;

import com.google.common.collect.ImmutableMap;

public class StandardLib {

  public static final Map<String, NativeFunction> NATIVE_FUNCTIONS = ImmutableMap
      .<String, NativeFunction> builder().putAll(MathFunctions.FUNCTION_MAP)
      .putAll(GrammarFunctions.FUNCTION_MAP).putAll(
          PreprocessorFunctions.FUNCTION_MAP).build();

  private StandardLib() {
  }
}
