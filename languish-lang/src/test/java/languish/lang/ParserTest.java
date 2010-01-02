package languish.lang;

import junit.framework.TestCase;
import languish.base.Term;
import languish.interpreter.Interpreter;
import languish.interpreter.Modules;
import languish.interpreter.error.DependencyUnavailableError;
import languish.lib.LanguishLoadError;
import languish.lib.testing.LibTestUtil;
import languish.tools.testing.TestUtil;
import languish.util.PrimitiveTree;

public class ParserTest extends TestCase {
  private static final Term LIB;

  static {
    try {
      LIB =
          Interpreter.reduceModuleCompletely(
              Modules.loadAndReturn("parser/parser_test"),
              LibTestUtil.STANDARD_INCLUDE);
    } catch (DependencyUnavailableError e) {
      throw new LanguishLoadError(e);
    }
  }

  public void testParser() {
    TestUtil.assertReducesToData(PrimitiveTree.from(42), LIB);
  }
}
