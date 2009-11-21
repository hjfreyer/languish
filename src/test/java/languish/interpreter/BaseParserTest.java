package languish.interpreter;

import static languish.util.Lambda.*;
import junit.framework.TestCase;
import languish.lambda.Term;
import languish.primitives.LSymbol;
import languish.util.JavaWrapper;
import languish.util.Lambda;

import org.jmock.Mockery;

import com.hjfreyer.util.Pair;

public class BaseParserTest extends TestCase {
  Mockery context = new Mockery();

  public void testGetParserAndProgram() {
    assertEquals(Pair.of("foo", "\nblah blah blah"), BaseParser
        .getParserAndProgram("#lang foo;;\nblah blah blah"));

    assertEquals(Pair.of("__BUILTIN__", "#lanr; \nblah blah blah\n\n  "),
        BaseParser.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  }

  public void testParseBuiltinParser() throws Exception {
    Term res =
        BaseParser
            .parseFromString("#lang __BUILTIN__;; [PRIMITIVE \"Returned as-is\" NULL]");

    assertEquals(JavaWrapper.of("Returned as-is"), Lambda
        .convertTermToJavaObject(res));
  }

  public void testParseNonstandardParser() throws Exception {
    Term res = BaseParser //
        .parseFromString("#lang fooParser;; blah blah blah");

    Term depList = cons(primitive(LSymbol.of("fooParser")), Term.NULL);
    Term programApplication =
        abs(app(ref(1), primitive(LSymbol.of(" blah blah blah"))));

    Term expected = cons(primitive(LSymbol.of("LOAD")), //
        cons(depList, cons(programApplication, Term.NULL)));

    assertEquals(expected, res);
  }

}
