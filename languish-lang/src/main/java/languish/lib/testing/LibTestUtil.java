package languish.lib.testing;

import languish.api.StandardLib;
import languish.base.Term;
import languish.interpreter.FileSystemDependencyManager;
import languish.interpreter.Interpreter;
import languish.interpreter.DependencyManager;
import languish.interpreter.error.DependencyUnavailableError;
import languish.lib.LanguishLoadError;

import com.google.common.collect.ImmutableList;

public class LibTestUtil {

  public static final DependencyManager LANGUISH_FOLDER = new FileSystemDependencyManager(
      ImmutableList.of("lish"));

  public static Term loadLib(String libName) {
    try {
      return Interpreter.loadAndInterpret(libName, LibTestUtil.LANGUISH_FOLDER,
          StandardLib.NATIVE_FUNCTIONS);
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }
}
