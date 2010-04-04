package languish.compiler;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.hjfreyer.util.Pair;

public class BaseParserTest extends TestCase {

	public void testGetParserAndProgram() {
		Assert.assertEquals(Pair.of("foo", "\nblah blah blah"), BaseParser
				.getParserAndProgram("#lang foo;;\nblah blah blah"));
	}

	public void testGetParserAndProgramDefault() {
		Assert.assertEquals(
				Pair.of(null, "#lanr; \nblah blah blah\n\n  "),
				BaseParser.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
	}

}
