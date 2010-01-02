package languish.lib.testing;

import languish.api.StandardLib;
import languish.base.Term;
import languish.depman.ChainedDependencyManager;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;
import languish.interpreter.Interpreter;
import languish.interpreter.Modules;
import languish.interpreter.error.DependencyUnavailableError;
import languish.lib.LanguishLoadError;

import com.google.common.collect.ImmutableList;

public class LibTestUtil {

  public static final DependencyManager LANGUISH_FOLDER =
      new FileSystemDependencyManager(ImmutableList.of("lish"));

  public static final DependencyManager STANDARD_INCLUDE =
      new ChainedDependencyManager(ImmutableList.of(
          StandardLib.NATIVE_INCLUDE,
          LANGUISH_FOLDER));

  public static Term loadLib(String libName) {
    try {
      return Interpreter.reduceModuleCompletely(
          Modules.loadAndReturn(libName),
          LibTestUtil.STANDARD_INCLUDE);
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }
}
