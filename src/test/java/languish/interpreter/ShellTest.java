package languish.interpreter;

import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import languish.primitives.LInteger;

public class ShellTest extends TestCase {
  // public void testTestLish() {
  // InputStream stream =
  // getClass().getClassLoader().getResourceAsStream("languish/test.lish");
  //
  // Shell s = new Shell(new InputStreamReader(stream));
  //
  // assertEquals(Tuple.of(), s.getLast());
  // }

  public void testBaseGrammarLish() {
    InputStream stream =
        getClass().getClassLoader().getResourceAsStream(
            "languish/base_grammar.lish");

    Shell s = new Shell(new InputStreamReader(stream));

    assertEquals(LInteger.of(42), s.getLast());
  }
}