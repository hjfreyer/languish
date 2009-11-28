package languish.tools.parsing;

import java.util.List;

import languish.base.Term;
import languish.parsing.Expression;
import languish.parsing.GrammarModule;
import languish.parsing.Production;
import languish.util.PrimitiveTree;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.hjfreyer.util.Pair;

public class TermParser {
  @SuppressWarnings("unchecked")
  public static final List<Pair<String, String>> TOKEN_PAIRS =
      ImmutableList.of( //
          Pair.of("[", "\\["),
          Pair.of("]", "\\]"),
          Pair.of("ABS", "ABS"),
          Pair.of("APP", "APP"),
          Pair.of("EQUALS", "EQUALS"),
          Pair.of("NATIVE_APPLY", "NATIVE_APPLY"),
          Pair.of("PRIMITIVE", "PRIMITIVE"),
          Pair.of("REF", "REF"),
          Pair.of("NULL", "NULL"),
          Pair.of("STRING_LIT", "\"(((\\\\.)|[^\"\\\\])*)\""),
          Pair.of("INTEGER_LIT", "[0-9]+"));

  // TODO(hjfreyer): Add block comment
  public static final List<String> DELIM = ImmutableList.of("\\s*", "//.*$");

  public static final List<Production> OPERATIONS =
      ImmutableList.of( //
          new Production("OPERATION", "ABS_OP", Expression.term("ABS")),
          new Production("OPERATION", "APP_OP", Expression.term("APP")),
          new Production("OPERATION", "EQUALS_OP", Expression.term("EQUALS")),
          new Production("OPERATION", "NATIVE_APPLY_OP", Expression
              .term("NATIVE_APPLY")));

  public static final List<Production> PRIMITIVES = ImmutableList.of( //
      new Production("PRIM_LIT", "STRING", Expression.term("STRING_LIT")),
      new Production("PRIM_LIT", "INTEGER", Expression.term("INTEGER_LIT")));

  public static final List<Production> TERMS = ImmutableList.of( //
      new Production("TERM", "NULL_TERM", Expression.term("NULL")),
      new Production("TERM", "PRIMITIVE_TERM", Expression.seq( //
          Expression.term("["),
          Expression.term("PRIMITIVE"),
          Expression.nonterm("PRIM_LIT"),
          Expression.nonterm("TERM"),
          Expression.term("]"))),
      new Production("TERM", "REF_TERM", Expression.seq( //
          Expression.term("["),
          Expression.term("REF"),
          Expression.term("INTEGER_LIT"),
          Expression.nonterm("TERM"),
          Expression.term("]"))),
      new Production("TERM", "TERM_PROPER", Expression.seq( //
          Expression.term("["),
          Expression.nonterm("OPERATION"),
          Expression.nonterm("TERM"),
          Expression.nonterm("TERM"),
          Expression.term("]"))));

  @SuppressWarnings("unchecked")
  public static final GrammarModule TERM_GRAMMAR =
      new GrammarModule("TERM", TOKEN_PAIRS, DELIM, ImmutableList
          .copyOf(Iterables.concat(OPERATIONS, PRIMITIVES, TERMS)));

  public static Parser<Term> getTermParser() {
    return TERM_GRAMMAR.getAstParser().map(new Map<PrimitiveTree, Term>() {
      @Override
      public Term map(PrimitiveTree from) {
        return TermSemantic.termFromAST(from);
      }
    });
  }

  private TermParser() {
  }
}
