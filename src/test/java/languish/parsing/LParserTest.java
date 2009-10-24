package languish.parsing;

import java.util.List;

import junit.framework.TestCase;

import org.codehaus.jparsec.Parser;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class LParserTest extends TestCase {
  public void testFoo() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("\\s+");

    List<GrammarRule> rules =
        ImmutableList.of(new GrammarRule("ROOT", "FOODER", new ParserTree(
            ParserTree.Op.TERM, "FOOO")));

    LParser parser = new LParser("ROOT", tokens, delim, rules);

    Parser<ASTNode> lexer = parser.getParser();

    assertEquals(new ASTNode("FOODER", new ASTNode("FOOO", "foo")), lexer
        .parse("foo"));
  }
}
