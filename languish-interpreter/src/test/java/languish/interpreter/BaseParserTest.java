package languish.interpreter;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jmock.Mockery;

import com.hjfreyer.util.Pair;

public class BaseParserTest extends TestCase {
  Mockery context = new Mockery();

  public void testGetParserAndProgram() {
    Assert.assertEquals(Pair.of("foo", "\nblah blah blah"), BaseParser
        .getParserAndProgram("#lang foo;;\nblah blah blah"));

    Assert.assertEquals(Pair.of("native/parsers/term_parser",
        "#lanr; \nblah blah blah\n\n  "), BaseParser
        .getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  }
}
