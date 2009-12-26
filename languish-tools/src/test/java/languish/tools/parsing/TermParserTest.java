package languish.tools.parsing;

import java.util.List;

import junit.framework.TestCase;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Tree;import languish.base.Primitive;

public class TermParserTest extends TestCase {
  @SuppressWarnings("unchecked")
  public void testNull() {
    Tree<String> expected =
        Tree.inode(Tree.leaf("NULL_TERM"), Tree.inode(Tree.inode(Tree
            .leaf("NULL"), Tree.leaf("NULL"))));

    assertEquals(expected, TermParser.TERM_GRAMMAR.getAstParser().parse("NULL"));
  }

  @SuppressWarnings("unchecked")
  public void testStringPrimtive() {
    Tree<String> ast =
        TermParser.TERM_GRAMMAR.getAstParser().parse(
            "[PRIMITIVE \"foobar\" NULL]");

    List<?> expected = ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of( //
        ImmutableList.of("[", "["),
        ImmutableList.of("PRIMITIVE", "PRIMITIVE"),
        ImmutableList.of("STRING_PRIM", ImmutableList.of( //
            ImmutableList.of("STRING_LIT", "\"foobar\""))),
        ImmutableList.of("NULL_TERM", ImmutableList.of( //
            ImmutableList.of("NULL", "NULL"))),
        ImmutableList.of("]", "]")));

    assertEquals(Tree.copyOf(expected), ast);
  }

  @SuppressWarnings("unchecked")
  public void testIntPrimtive() {
    Tree<String> ast =
        TermParser.TERM_GRAMMAR.getAstParser().parse("[PRIMITIVE 42 NULL]");

    List<?> expected = ImmutableList.of("PRIMITIVE_TERM", ImmutableList.of( //
        ImmutableList.of("[", "["),
        ImmutableList.of("PRIMITIVE", "PRIMITIVE"),
        ImmutableList.of("INTEGER_PRIM", ImmutableList.of( //
            ImmutableList.of("INTEGER_LIT", "42"))),
        ImmutableList.of("NULL_TERM", ImmutableList.of( //
            ImmutableList.of("NULL", "NULL"))),
        ImmutableList.of("]", "]")));

    assertEquals(Tree.copyOf(expected).toString(), ast.toString());
  }
}