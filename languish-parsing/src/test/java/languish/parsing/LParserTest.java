package languish.parsing;

import java.util.List;

import junit.framework.TestCase;
import languish.util.PrimitiveTree;

import org.codehaus.jparsec.Parser;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class LParserTest extends TestCase {
  public void testFoo() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("\\s+");

    List<Production> rules =
        ImmutableList.of(new Production("ROOT", "FOODER", new Expression(
            Expression.Op.TERM, "FOOO")));

    GrammarModule parser = new GrammarModule("ROOT", tokens, delim, rules);

    Parser<PrimitiveTree> lexer = parser.getParser();
    //
    // assertEquals(new ASTNode("FOODER", ImmutableList.of(new ASTNode("FOOO",
    // "foo"))), lexer.parse("foo"));
  }

  // public void testLanguishTestFile() throws Exception {
  // TestUtil.assertReducesToData(LSymbol.of("foo"), DEPMAN
  // .getResource("parser/parser_test"));
  // }
}
