package languish.tools.parsing;

import java.util.List;

import languish.base.Term;
import languish.parsing.GrammarModule;
import languish.parsing.Sequence;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

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
					Pair.of("PRINT", "PRINT"),
					Pair.of("NULL", "NULL"),
					Pair.of("TRUE", "TRUE"),
					Pair.of("FALSE", "FALSE"),
					Pair.of("STRING_LIT", "\"(((\\\\.)|[^\"\\\\])*)\""),
					Pair.of("INTEGER_LIT", "[0-9]+"));

	// TODO(hjfreyer): Add block comment
	public static final List<String> DELIM = ImmutableList.of("(\\s|//[^\n]*)*");

	public static final List<Sequence> OPERATIONS = ImmutableList.of( //
			Sequence.of("OPERATION", "ABS_OP", "ABS"),
			Sequence.of("OPERATION", "APP_OP", "APP"),
			Sequence.of("OPERATION", "EQUALS_OP", "EQUALS"),
			Sequence.of("OPERATION", "PRINT_OP", "PRINT"),
			Sequence.of("OPERATION", "NATIVE_APPLY_OP", "NATIVE_APPLY"));

	public static final List<Sequence> BOOLEAN_LIT = ImmutableList.of( //
			Sequence.of("BOOLEAN_LIT", "TRUE_LIT", "TRUE"),
			Sequence.of("BOOLEAN_LIT", "FALSE_LIT", "FALSE"));

	public static final List<Sequence> PRIMITIVES = ImmutableList.of( //
			Sequence.of("PRIM_LIT", "STRING_PRIM", "STRING_LIT"),
			Sequence.of("PRIM_LIT", "INTEGER_PRIM", "INTEGER_LIT"),
			Sequence.of("PRIM_LIT", "BOOLEAN_PRIM", "BOOLEAN_LIT"));

	public static final List<Sequence> TERMS = ImmutableList.of( //
			Sequence.of("TERM", "NULL_TERM", "NULL"),
			Sequence.of("TERM", "PRIMITIVE_TERM", //
					"[",
					"PRIMITIVE",
					"PRIM_LIT",
					"TERM",
					"]"),
			Sequence.of("TERM", "REF_TERM", //
					"[",
					"REF",
					"INTEGER_LIT",
					"TERM",
					"]"),
			Sequence.of("TERM", "TERM_PROPER", //
					"[",
					"OPERATION",
					"TERM",
					"TERM",
					"]"));

	@SuppressWarnings("unchecked")
	public static final GrammarModule TERM_GRAMMAR =
			new GrammarModule("TERM", TOKEN_PAIRS, DELIM, ImmutableList
					.copyOf(Iterables.concat(OPERATIONS, BOOLEAN_LIT, PRIMITIVES, TERMS)));

	public static Parser<Term> getTermParser() {
		return TERM_GRAMMAR.getAstParser().map(new Map<Tree<String>, Term>() {
			@Override
			public Term map(Tree<String> from) {
				return (Term) TermSemantic.TERM_SEMANTIC.process(from);
			}
		});
	}

	private TermParser() {
	}
}
