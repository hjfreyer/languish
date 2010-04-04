package languish.parsing.api;

import java.util.List;

import junit.framework.TestCase;
import languish.parsing.api.GrammarModule;
import languish.parsing.api.Sequence;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.error.ParserException;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

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
        Lists.transform(
            lexer.parse("foo fooooooo      foooo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "fooooooo", "foooo"), actual);
  }

  public void testLexerLineEndComment() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "foo"));
    List<String> delim = ImmutableList.of("\\s*//[^\n]*\\s*", "\\s+");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(
            lexer.parse("foo foo //Commented \n     foo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "foo", "foo"), actual);

    actual =
        Lists.transform(
            lexer.parse("foo foo //Commented \n \n   \n    foo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "foo", "foo"), actual);

    actual =
        Lists.transform(
            lexer.parse("foo foo //Commented    foo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "foo"), actual);
  }

  public void testRegexLexerOptionalSpace() {
    List<Pair<String, String>> tokens =
        ImmutableList.of(Pair.of("FOOO", "fo*o"));
    List<String> delim = ImmutableList.of("\\s*");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(
            lexer.parse("foofooooooo      foooofo"),
            Functions.TO_STRING);

    assertEquals(ImmutableList.of("foo", "fooooooo", "foooo", "fo"), actual);
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

  @SuppressWarnings("unchecked")
  public void testCalculatorLexer() {
    List<Pair<String, String>> tokens = ImmutableList.of( //
        Pair.of("INT", "[0-9]+"),
        Pair.of("+", "\\+"),
        Pair.of("-", "-"),
        Pair.of("*", "\\*"),
        Pair.of("/", "/"),
        Pair.of("(", "\\("),
        Pair.of(")", "\\)"));
    List<String> delim = ImmutableList.of("\\s*");

    Parser<Void> delimParser = GrammarModule.getDelimiterParser(delim);
    Parser<Fragment> tokenizer = GrammarModule.getTokenizer(tokens);

    Parser<List<Token>> lexer = tokenizer.lexer(delimParser);

    List<String> actual =
        Lists.transform(lexer.parse("(3+42) * 16  /3+2"), Functions.TO_STRING);

    assertEquals(ImmutableList.of(
        "(",
        "3",
        "+",
        "42",
        ")",
        "*",
        "16",
        "/",
        "3",
        "+",
        "2"), actual);
  }

  @SuppressWarnings("unchecked")
  public static final List<Pair<String, String>> CALC_TOKENS =
      ImmutableList.of( //
          Pair.of("INT", "[0-9]+"),
          Pair.of("+", "\\+"),
          Pair.of("-", "-"),
          Pair.of("*", "\\*"),
          Pair.of("/", "/"),
          Pair.of("(", "\\("),
          Pair.of(")", "\\)"));
  public static final List<String> CALC_DELIM = ImmutableList.of("\\s*");

  public static final List<Sequence> CALC_RULES =
      ImmutableList.of(
          Sequence.of("EXPR", "EXPR", "FACTOR", "EXPR_TAIL"),
          Sequence.of("EXPR_TAIL", "SUM", "+", "FACTOR", "EXPR_TAIL"),
          Sequence.of("EXPR_TAIL", "DIFF", "-", "FACTOR", "EXPR_TAIL"),
          Sequence.of("EXPR_TAIL", "EMPTY_EXPR_TAIL"),
          Sequence.of("FACTOR", "FACTOR", "TERM", "FACTOR_TAIL"),
          Sequence.of("FACTOR_TAIL", "PROD", "*", "TERM", "FACTOR_TAIL"),
          Sequence.of("FACTOR_TAIL", "QUOT", "/", "TERM", "FACTOR_TAIL"),
          Sequence.of("FACTOR_TAIL", "EMPTY_FACTOR_TAIL"),
          Sequence.of("TERM", "LITERAL", "INT"),
          Sequence.of("TERM", "PAREN", "(", "EXPR", ")"));

  public static final GrammarModule CALC_GRAMMAR =
      new GrammarModule("EXPR", CALC_TOKENS, CALC_DELIM, CALC_RULES);

  @SuppressWarnings("unchecked")
  public void testCalculatorGrammarLiteral() {
    Tree<String> expected =
        Tree.copyOf(ImmutableList.of("EXPR", ImmutableList.of( //
            ImmutableList.of("FACTOR", ImmutableList.of( //
                ImmutableList.of("LITERAL", ImmutableList.of( //
                    ImmutableList.of("INT", "123"))),
                ImmutableList.of("EMPTY_FACTOR_TAIL", ImmutableList.of()))),
            ImmutableList.of("EMPTY_EXPR_TAIL", ImmutableList.of()))));

    assertEquals(expected, CALC_GRAMMAR.getAstParser().parse("123"));
  }

  @SuppressWarnings("unchecked")
  public void testCalculatorGrammarSum() {

    Tree<String> expected =
        Tree.inode(Tree.leaf("EXPR"), Tree.inode( //
            Tree.inode(Tree.leaf("FACTOR"), Tree.inode( //
                Tree.inode(Tree.leaf("LITERAL"), Tree.inode( //
                    Tree.inode(Tree.leaf("INT"), Tree.leaf("123")))),
                Tree.inode(Tree.leaf("EMPTY_FACTOR_TAIL"), Tree
                    .<String> inode()))),
            Tree.inode(Tree.leaf("SUM"), Tree
                .inode( //
                    Tree.inode(Tree.leaf("+"), Tree.leaf("+")),
                    Tree.inode(Tree.leaf("FACTOR"), Tree.inode( //
                        Tree.inode(Tree.leaf("LITERAL"), Tree.inode( //
                            Tree.inode(Tree.leaf("INT"), Tree.leaf("345")))),
                        Tree.inode(Tree.leaf("EMPTY_FACTOR_TAIL"), Tree
                            .<String> inode()))),
                    Tree.inode(Tree.leaf("EMPTY_EXPR_TAIL"), Tree
                        .<String> inode())))));

    assertEquals(expected, CALC_GRAMMAR.getAstParser().parse("123+345"));
  }

  @SuppressWarnings("unchecked")
  public void testCalculatorGrammar() {
    Tree<String> expected =
        Tree.inode(Tree.leaf("EXPR"), Tree.inode(Tree.inode(
            Tree.leaf("FACTOR"),
            Tree.inode(Tree.inode(Tree.leaf("PAREN"), Tree.inode(Tree.inode(
                Tree.leaf("("),
                Tree.leaf("(")), Tree.inode(Tree.leaf("EXPR"), Tree.inode(Tree
                .inode(Tree.leaf("FACTOR"), Tree.inode(Tree.inode(Tree
                    .leaf("LITERAL"), Tree.inode(Tree.inode(
                    Tree.leaf("INT"),
                    Tree.leaf("3")))), Tree.inode(Tree
                    .leaf("EMPTY_FACTOR_TAIL"), Tree.<String> inode()))), Tree
                .inode(Tree.leaf("SUM"), Tree.inode(Tree.inode(
                    Tree.leaf("+"),
                    Tree.leaf("+")), Tree.inode(Tree.leaf("FACTOR"), Tree
                    .inode(Tree.inode(Tree.leaf("LITERAL"), Tree.inode(Tree
                        .inode(Tree.leaf("INT"), Tree.leaf("42")))), Tree
                        .inode(Tree.leaf("EMPTY_FACTOR_TAIL"), Tree
                            .<String> inode()))), Tree.inode(Tree
                    .leaf("EMPTY_EXPR_TAIL"), Tree.<String> inode()))))), Tree
                .inode(Tree.leaf(")"), Tree.leaf(")")))), Tree.inode(Tree
                .leaf("PROD"), Tree.inode(Tree.inode(Tree.leaf("*"), Tree
                .leaf("*")), Tree.inode(Tree.leaf("LITERAL"), Tree.inode(Tree
                .inode(Tree.leaf("INT"), Tree.leaf("16")))), Tree.inode(Tree
                .leaf("QUOT"), Tree.inode(Tree.inode(Tree.leaf("/"), Tree
                .leaf("/")), Tree.inode(Tree.leaf("LITERAL"), Tree.inode(Tree
                .inode(Tree.leaf("INT"), Tree.leaf("3")))), Tree.inode(Tree
                .leaf("EMPTY_FACTOR_TAIL"), Tree.<String> inode()))))))), Tree
            .inode(Tree.leaf("SUM"), Tree.inode(Tree.inode(Tree.leaf("+"), Tree
                .leaf("+")), Tree.inode(Tree.leaf("FACTOR"), Tree.inode(Tree
                .inode(Tree.leaf("LITERAL"), Tree.inode(Tree.inode(Tree
                    .leaf("INT"), Tree.leaf("2")))), Tree.inode(Tree
                .leaf("EMPTY_FACTOR_TAIL"), Tree.<String> inode()))), Tree
                .inode(Tree.leaf("EMPTY_EXPR_TAIL"), Tree.<String> inode())))));

    assertEquals(expected, CALC_GRAMMAR.getAstParser().parse(
        "(3+42) * 16  /3+2"));
  }
}
