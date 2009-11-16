package languish.parsing;

import java.util.List;

import junit.framework.TestCase;
import languish.api.parser.ASTNode;
import languish.api.parser.GrammarRule;
import languish.api.parser.LParser;
import languish.api.parser.ParserTree;
import languish.interpreter.DependencyManager;
import languish.interpreter.FileSystemDependencyManager;

import org.codehaus.jparsec.Parser;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class LParserTest extends TestCase {
  private static final DependencyManager DEPMAN =
      new FileSystemDependencyManager(ImmutableList.of("languish"));

  public void testFoo() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("\\s+");

    List<GrammarRule> rules =
        ImmutableList.of(new GrammarRule("ROOT", "FOODER", new ParserTree(
            ParserTree.Op.TERM, "FOOO")));

    LParser parser = new LParser("ROOT", tokens, delim, rules);

    Parser<ASTNode> lexer = parser.getParser();

    assertEquals(new ASTNode("FOODER", ImmutableList.of(new ASTNode("FOOO",
        "foo"))), lexer.parse("foo"));
  }

  // public void testLanguishTestFile() throws Exception {
  // TestUtil.assertReducesToData(LSymbol.of("foo"), DEPMAN
  // .getResource("parser/parser_test"));
  // }
}
