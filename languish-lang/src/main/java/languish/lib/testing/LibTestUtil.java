package languish.lib.testing;

import languish.api.StandardLib;
import languish.depman.ChainedDependencyManager;
import languish.depman.FileSystemDependencyManager;
import languish.interpreter.DependencyManager;

import com.google.common.collect.ImmutableList;

public class LibTestUtil {

  public static final DependencyManager LANGUISH_FOLDER =
      new FileSystemDependencyManager(ImmutableList.of("languish"));

  public static final DependencyManager STANDARD_INCLUDE =
      new ChainedDependencyManager(ImmutableList.of(
          StandardLib.DEFAULT_INCLUDE,
          LANGUISH_FOLDER));
}
