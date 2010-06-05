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
			Pair.of("(", "("),
			Pair.of(")", ")"));

	public static final List<Sequence> RULES =
			ImmutableList.of( //
					// Compilation unit
					Sequence.of(
							"NamespaceGrammar.COMPILATION_UNIT",
							"NamespaceGrammar.COMPILATION_UNIT",
							"IMPORT_STATEMENT",
							"TERM"),
					// Terms
					Sequence.of("TERM", "TERM", "(", "TERM", ")"),
					Sequence.of(
							"TERM",
							"SET_TERM",
							"[",
							"SET",
							"TERM",
							"TERM",
							"TERM",
							"]"),
					Sequence.of("TERM", "SELF_TERM", "SELF"));

	public static final GrammarModule GRAMMAR =
			LambdaPlusGrammar.GRAMMAR.extend(new GrammarModule(
					"NamespaceGrammar.COMPILATION_UNIT",
					TOKENS,
					ImmutableList.<String> of(),
					RULES));

}
