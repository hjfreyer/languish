package languish.parsing;

import java.util.List;

import junit.framework.TestCase;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.error.ParserException;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;

public class GrammarModuleTest extends TestCase {

  public void testSimpleLexer() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("\\s+");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(lexer.parse("foo foo      foo"), Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "foo", "foo"), actual);
  }

  public void testLexerWithWeirdDelimeter() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("a");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(lexer.parse("fooafooafoo"), Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "foo", "foo"), actual);
  }

  public void testRegexLexer() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "fo*o"));
    List<String> delim = ImmutableList.of("\\s+");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(lexer.parse("foo fooooooo      foooo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "fooooooo", "foooo"), actual);
  }

  public void testLexerReject() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "fo*o"));
    List<String> delim = ImmutableList.of("\\s+");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    try {
      lexer.parse("foo fooooooo      fa");
      fail();
    } catch (ParserException pe) {
    }
  }

  @SuppressWarnings("unchecked")
  public void testLexerMultipleTokens() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "fo*o"), Pair.of("BAR", "bar"));
    List<String> delim = ImmutableList.of("\\s+");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(lexer.parse("foo bar      foooo"), Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "bar", "foooo"), actual);
  }
}
