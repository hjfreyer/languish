package languish.interpreter;

import static languish.base.Terms.*;
import junit.framework.TestCase;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.util.PrimitiveTree;

import org.jmock.Mockery;

import com.hjfreyer.util.Pair;

public class BaseParserTest extends TestCase {
  Mockery context = new Mockery();

  public void testGetParserAndProgram() {
    assertEquals(Pair.of("foo", "\nblah blah blah"), BaseParser
        .getParserAndProgram("#lang foo;;\nblah blah blah"));

    assertEquals(
        Pair.of("__BUILTIN__", "#lanr; \nblah blah blah\n\n  "),
        BaseParser.getParserAndProgram("#lanr; \nblah blah blah\n\n  "));
  }

  public void testParseBuiltinParser() throws Exception {
    Term res =
        BaseParser.parseFromString("#lang __BUILTIN__;; "
            + "[PRIMITIVE \"Returned as-is\" NULL]");

    assertEquals(PrimitiveTree.of(new Primitive("Returned as-is")), Terms
        .convertTermToJavaObject(res));
  }

  public void testParseNonstandardParser() throws Exception {
    Term res = BaseParser //
        .parseFromString("#lang fooParser;; blah blah blah");

    Term depList = cons(primitive(new Primitive("fooParser")), Term.NULL);
    Term programApplication =
        abs(app(ref(1), primitive(new Primitive(" blah blah blah"))));

    Term expected = cons(primitive(new Primitive("LOAD")), //
        cons(depList, cons(programApplication, Term.NULL)));

    assertEquals(expected, res);
  }

}
