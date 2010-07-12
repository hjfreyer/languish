package languish.lib.bootstrap.namespaces;

import java.util.List;

import languish.lib.bootstrap.lambdaplus.LambdaPlusGrammar;
import languish.parsing.api.GrammarModule;
import languish.parsing.api.Sequence;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class NamespaceGrammar {

	public static final List<Pair<String, String>> TOKENS = ImmutableList.of( //
			Pair.of("Namespaces.NATIVE_KEYWORD", "___NATIVE___"),
			Pair.of("Namespaces.IMPORT_KEYWORD", "import"),
			Pair.of("DOT", "[.]"),
			Pair.of(";", "[;]"),
			Pair.of("=", "[=]"),
			Pair.of("IDENT", "[a-zA-Z_][a-zA-Z0-9_]*"),
			Pair.of("(", "[(]"),
			Pair.of(")", "[)]"));

	public static final List<Sequence> RULES =
			ImmutableList
					.of(
							// Compilation unit
							Sequence.of(
									"Namespaces.COMPILATION_UNIT",
									"Namespaces.COMPILATION_UNIT",
									"Namespaces.IMPORT_LIST",
									"Namespaces.EXPRESSION"),
							// Imports
							Sequence.of(
									"Namespaces.IMPORT_LIST",
									"Namespaces.IMPORT_LIST",
									"Namespaces.IMPORT",
									"Namespaces.IMPORT_LIST"),
							Sequence.of(
									"Namespaces.IMPORT_LIST",
									"Namespaces.IMPORT_LIST.EMPTY"),
							Sequence.of(
									"Namespaces.IMPORT",
									"Namespaces.IMPORT",
									"Namespaces.IMPORT_KEYWORD",
									"QUALIFIED_IDENTIFIER",
									";"),
							// GENERAL STUFF
							Sequence.of(
									"QUALIFIED_IDENTIFIER",
									"QUALIFIED_IDENTIFIER",
									"IDENT",
									"QUALIFIED_IDENTIFIER.TAIL"),
							Sequence.of(
									"QUALIFIED_IDENTIFIER.TAIL",
									"QUALIFIED_IDENTIFIER.TAIL",
									"DOT",
									"IDENT",
									"QUALIFIED_IDENTIFIER.TAIL"),
							Sequence.of(
									"QUALIFIED_IDENTIFIER.TAIL",
									"QUALIFIED_IDENTIFIER.TAIL.EMPTY"),
							// Expression
							Sequence.of(
									"Namespaces.EXPRESSION",
									"Namespaces.EXPRESSION",
									"Namespaces.EXPRESSION.BASE",
									"Namespaces.SELECTOR_LIST"),

							// Sequence.of(
							// "Namespaces.EXPRESSION.BASE",
							// "Namespaces.NATIVE_TERM",
							// "Namespaces.NATIVE_KEYWORD",
							// "(",
							// "TERM",
							// ")"),

							// Base expression
							Sequence.of(
									"Namespaces.EXPRESSION.BASE",
									"Namespaces.EXPRESSION.BASE.STRING_LITERAL",
									"STRING_LIT"),
							Sequence.of(
									"Namespaces.EXPRESSION.BASE",
									"Namespaces.EXPRESSION.BASE.IDENTIFIER_GET",
									"IDENT"),

							// Selectors
							Sequence.of(
									"Namespaces.SELECTOR_LIST",
									"Namespaces.SELECTOR_LIST",
									"Namespaces.SELECTOR",
									"Namespaces.SELECTOR_LIST"),
							Sequence.of(
									"Namespaces.SELECTOR_LIST",
									"Namespaces.SELECTOR_LIST.EMPTY"),

							// Function call
							Sequence.of(
									"Namespaces.SELECTOR",
									"Namespaces.SELECTOR.FUNCTION_CALL",
									"(",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST",
									")"),

							// Argument lists
							Sequence.of(
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL"),
							Sequence.of(
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.EMPTY"),
							Sequence.of(
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL",
									",",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL"),
							Sequence.of(
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.TAIL.EMPTY"),

							// Argument
							Sequence.of(
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG",
									"Namespaces.SELECTOR.FUNCTION_CALL.ARG",
									"IDENT",
									"=",
									"Namespaces.EXPRESSION"));

	public static final GrammarModule GRAMMAR = LambdaPlusGrammar.GRAMMAR
			.extend(new GrammarModule(
					"Namespaces.COMPILATION_UNIT",
					TOKENS,
					ImmutableList.<String> of(),
					RULES));

}
