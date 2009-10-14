package languish.interpreter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import junit.framework.TestCase;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.base.Util;
import languish.parsing.LParser;
import languish.parsing.LParsers;
import languish.primitives.LInteger;
import languish.testing.TestUtil;

public class ShellTest extends TestCase {
  // public void testTestLish() {
  // InputStream stream =
  // getClass().getClassLoader().getResourceAsStream("languish/test.lish");
  //
  // Shell s = new Shell(new InputStreamReader(stream));
  //
  // assertEquals(Tuple.of(), s.getLast());
  // }

  public void testBaseGrammarLish() throws Exception {
    InputStream stream =
        getClass().getClassLoader().getResourceAsStream(
            "languish/base_grammar.lish");

    Tuple grammar = Shell.processReadable(new InputStreamReader(stream));

    grammar = Lambda.reduce(grammar);

    LParser parser =
        LParsers.fromListStructure((List<?>) Util
            .convertPrimitiveToJava(grammar));

    // assertEquals(null, parser.getParser().parse("foo"));
  }

  public void testBaseParserLish() throws Exception {
    InputStream stream =
        getClass().getClassLoader().getResourceAsStream(
            "languish/base_parser_test.lish");

    Tuple value = Shell.processReadable(new InputStreamReader(stream));
    TestUtil.assertReducesToData(LInteger.of(42), value);
  }
}
//
// [CONS [DATA "STATEMENT"]
// [CONS [CONS
//
// [CONS [DATA "REDUCE"]
// [CONS [CONS [CONS [DATA "PRIM_GET"]
// [CONS [CONS [CONS [DATA "TEXT_NODE"] [CONS [DATA "APP"]
// [DATA []]]]
// [CONS [CONS [DATA "TEXT_NODE"] [CONS [DATA "ABS"] [DATA []]]]
// [DATA []]]]
// [DATA []]]] [DATA []]] [DATA []]]] [DATA []]] [DATA []]]]>
//
//
