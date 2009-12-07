package languish.tools.parsing;

import java.util.List;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Tree;

public class TermParserTest extends TestCase {
  public void testNull() {
    Tree<String> ast = TermParser.TERM_GRAMMAR.getAstParser().parse("NULL");

    List<?> expected = ImmutableList.of("NULL_TERM", ImmutableList.of("NULL"));

    assertEquals(Tree.copyOf(expected), ast);
  }

  public void testStringPrimtive() {
    Tree<String> ast =
        TermParser.TERM_GRAMMAR.getAstParser().parse(
            "[PRIMITIVE \"foobar\" NULL]");

    List<?> expected =
        ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of(
            "[",
            "PRIMITIVE",
            ImmutableList.of("STRING", ImmutableList.of("\"foobar\"")),
            ImmutableList.of("NULL_TERM", ImmutableList.of("NULL")),
            "]"));

    assertEquals(Tree.copyOf(expected), ast);
  }

  public void testIntPrimtive() {
    Tree<String> ast =
        TermParser.TERM_GRAMMAR.getAstParser().parse("[PRIMITIVE 42 NULL]");

    List<?> expected =
        ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of(
            "[",
            "PRIMITIVE",
            ImmutableList.of("INTEGER", ImmutableList.of("42")),
            ImmutableList.of("NULL_TERM", ImmutableList.of("NULL")),
            "]"));

    assertEquals(Tree.copyOf(expected), ast);
  }
}