package languish.lib.bootstrap.namespaces;

import java.util.List;

import languish.lib.bootstrap.lambdaplus.LambdaPlusGrammar;
import languish.parsing.api.GrammarModule;
import languish.parsing.api.Sequence;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class NamespaceGrammar {

	@SuppressWarnings("unchecked")
	public static final List<Pair<String, String>> TOKENS = ImmutableList.of( // 
			Pair.of("Namespaces.NATIVE_KEYWORD", "___NATIVE___"),
			Pair.of("DOT", "."),
			Pair.of("IDENT", "[a-zA-Z_][a-zA-Z0-9_]*"),
			Pair.of("(", "("),
			Pair.of(")", ")"));

	public static final List<Sequence> RULES =
			ImmutableList.of(
			// Compilation unit
					Sequence.of(
							"Namespaces.COMPILATION_UNIT",
							"Namespaces.COMPILATION_UNIT",
							"IMPORT_STATEMENT",
							"TERM"),
					// Terms
					Sequence.of(
							"Namespaces.EXPRESSION.BASE",
							"NATIVE_TERM",
							"Namespaces.NATIVE_KEYWORD",
							"(",
							"TERM",
							")"),
					// Sequence.of(
					// "Namespaces.EXPRESSION.SELECTORS",
					// "Namespaces.EXPRESSION.SELECTORS.GET_ATTR",
					// "DOT",
					// "IDENT",
					// "Namespaces.EXPRESSION.SELECTORS"),
					Sequence.of(
							"Namespaces.EXPRESSION.SELECTORS",
							"Namespaces.EXPRESSION.SELECTORS.CALL_METHOD",
							"(",
							"Namespaces.EXPRESSION",
							")",
							"Namespaces.EXPRESSION.SELECTORS"),
					Sequence.of(
							"Namespaces.EXPRESSION.SELECTORS",
							"Namespaces.EXPRESSION.SELECTORS.Empty"),
					Sequence.of(
							"Namespaces.EXPRESSION",
							"Namespaces.EXPRESSION",
							"Namespaces.EXPRESSION.BASE",
							"Namespaces.EXPRESSION.SELECTORS"));

	public static final GrammarModule GRAMMAR =
			LambdaPlusGrammar.GRAMMAR.extend(new GrammarModule(
					"NamespaceGrammar.COMPILATION_UNIT",
					TOKENS,
					ImmutableList.<String> of(),
					RULES));

}
