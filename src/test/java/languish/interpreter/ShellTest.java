package languish.interpreter;

import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import languish.testing.TestConstants;

public class ShellTest extends TestCase {
  public void testFoo() {
    InputStream stream =
        getClass().getClassLoader().getResourceAsStream("languish/test.lish");

    Shell s = new Shell(new InputStreamReader(stream));

    assertEquals(TestConstants.FOUR, s.getLast());
  }

  public static void main(String[] args) {
    new ShellTest().testFoo();
  }
}
