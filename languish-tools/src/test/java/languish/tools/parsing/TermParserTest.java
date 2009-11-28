package languish.tools.parsing;

import java.util.List;

import junit.framework.TestCase;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableList;

public class TermParserTest extends TestCase {
  public void testNull() {
    PrimitiveTree ast = TermParser.TERM_GRAMMAR.getAstParser().parse("NULL");

    List<?> expected = ImmutableList.of("NULL_TERM", "NULL");

    assertEquals(PrimitiveTree.copyOf(expected), ast);
  }

  public void testStringPrimtive() {
    PrimitiveTree ast =
        TermParser.TERM_GRAMMAR.getAstParser()
            .parse("[PRIMITIVE \"foobar\" NULL]");

    List<?> expected =
        ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of(
            "[",
            "PRIMITIVE",
            ImmutableList.of("STRING", "\"foobar\""),
            ImmutableList.of("NULL_TERM", "NULL"),
            "]"));

    assertEquals(PrimitiveTree.copyOf(expected), ast);
  }

  public void testIntPrimtive() {
    PrimitiveTree ast =
        TermParser.TERM_GRAMMAR.getAstParser().parse("[PRIMITIVE 42 NULL]");

    List<?> expected =
        ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of(
            "[",
            "PRIMITIVE",
            ImmutableList.of("INTEGER", "42"),
            ImmutableList.of("NULL_TERM", "NULL"),
            "]"));

    assertEquals(PrimitiveTree.copyOf(expected), ast);
  }
}