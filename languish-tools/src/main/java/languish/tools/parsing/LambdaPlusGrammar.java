package languish.tools.parsing;

import java.util.List;

import languish.parsing.GrammarModule;
import languish.parsing.Sequence;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class LambdaPlusGrammar {

	@SuppressWarnings("unchecked")
	public static final List<Pair<String, String>> TOKENS = ImmutableList.of( // 
			Pair.of("[", "\\["),
			Pair.of("]", "\\]"),
			Pair.of("ABS", "ABS"),
			Pair.of("APP", "APP"),
			Pair.of("CONS", "CONS"),
			Pair.of("CAR", "CAR"),
			Pair.of("CDR", "CDR"),
			Pair.of("EQUALS", "EQUALS"),
			Pair.of("REF", "REF"),
			Pair.of("NULL", "NULL"),
			Pair.of("STRING_LIT", "\"(((\\\\.)|[^\"\\\\])*)\""),
			Pair.of("INTEGER_LIT", "[0-9]+"),
			Pair.of("IMPORT_DIRECTIVE", "#import"),
			Pair.of("IMPORT_IDENT", "[a-zA-Z0-9_/]+"),
			Pair.of(",", ","),
			Pair.of(";;", ";;"));

	// TODO(hjfreyer): Add block comment
	public static final List<String> DELIM = ImmutableList.of("(\\s|//[^\n]*)*");

	public static final List<Sequence> RULES =
			ImmutableList.of( //
					// Compilation unit
					Sequence.of(
							"LambdaPlusGrammar.COMPILATION_UNIT",
							"LambdaPlusGrammar.COMPILATION_UNIT",
							"IMPORT_STATEMENT",
							"TERM"),
					// Import stuff
					Sequence.of(
							"IMPORT_STATEMENT",
							"IMPORT_STATEMENT",
							"IMPORT_DIRECTIVE",
							"IMPORT_IDENT",
							"IMPORT_TAIL"),
					Sequence.of("IMPORT_STATEMENT", "EMPTY_IMPORT"),
					Sequence.of(
							"IMPORT_TAIL",
							"IMPORT_TAIL_CONT",
							",",
							"IMPORT_IDENT",
							"IMPORT_TAIL"),
					Sequence.of("IMPORT_TAIL", "IMPORT_TAIL_END", ";;"),
					// Terms
					Sequence.of("TERM", "ABS_TERM", "[", "ABS", "TERM", "]"),
					Sequence.of("TERM", "APP_TERM", "[", "APP", "TERM", "TERM", "]"),
					Sequence.of("TERM", "CONS_TERM", "[", "CONS", "TERM", "TERM", "]"),
					Sequence.of("TERM", "CAR_TERM", "[", "CAR", "TERM", "]"),
					Sequence.of("TERM", "CDR_TERM", "[", "CDR", "TERM", "]"),
					Sequence
							.of("TERM", "EQUALS_TERM", "[", "EQUALS", "TERM", "TERM", "]"),
					Sequence.of("TERM", "REF_TERM", "[", "REF", "INTEGER_LIT", "]"),
					Sequence.of("TERM", "NULL_TERM", "NULL"),
					Sequence.of("TERM", "STRING_LIT_TERM", "STRING_LIT")

			);

	public static final GrammarModule GRAMMAR =
			new GrammarModule(
					"LambdaPlusGrammar.COMPILATION_UNIT",
					TOKENS,
					DELIM,
					RULES);

}
