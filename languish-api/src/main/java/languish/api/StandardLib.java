package languish.api;

import languish.base.NativeFunction;
import languish.depman.NativeFunctionDependencyManager;

import com.google.common.collect.ImmutableMap;

public class StandardLib {

  public static final NativeFunctionDependencyManager NATIVE_INCLUDE =
      new NativeFunctionDependencyManager("__BUILTIN__/", ImmutableMap
          .<String, NativeFunction> builder()
          .putAll(MathFunctions.FUNCTION_MAP).putAll(
              GrammarFunctions.FUNCTION_MAP).build());

  private StandardLib() {
  }
}
