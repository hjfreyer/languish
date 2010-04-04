package languish.parsing.api;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class GrammarModules {

	public static Tree<String> moduleToStringTree(GrammarModule module) {
		String root = module.getRootRule();
		List<List<String>> tokenTypesList =
				Lists.transform(module.getTokenTypes(), Pair.<String> asList());
		List<String> ignored = module.getIgnored();
		List<List<String>> grammarRulesList =
				Lists.transform(
						module.getRules(),
						new Function<Sequence, List<String>>() {
							@Override
							public List<String> apply(Sequence from) {
								String nonterminal = from.getNonterminal();
								String name = from.getName();
								List<String> content = from.getContent();

								return ImmutableList.copyOf(Iterables.concat( //
										ImmutableList.of(nonterminal, name),
										content));
							}
						});

		return Tree.copyOf(ImmutableList.of(
				root,
				tokenTypesList,
				ignored,
				grammarRulesList));
	}

	public static GrammarModule moduleFromStringTree(Tree<String> struct) {
		String root = struct.asList().get(0).asLeaf();
		List<Tree<String>> tokenTypesList = struct.asList().get(1).asList();
		List<Tree<String>> ignored = struct.asList().get(2).asList();
		List<Tree<String>> grammarRulesList = struct.asList().get(3).asList();

		return new GrammarModule(
				root,
				tokenTypesFromStringTree(tokenTypesList),
				ignoredFromStringTree(ignored),
				sequencesFromStringTree(grammarRulesList));
	}

	private static List<String> ignoredFromStringTree(List<Tree<String>> struct) {
		return Lists.transform(struct, Tree.<String> asLeafFunction());
	}

	private static List<Sequence> sequencesFromStringTree(
			List<Tree<String>> struct) {
		return Lists.transform(struct, new Function<Tree<String>, Sequence>() {

			@Override
			public Sequence apply(Tree<String> from) {
				List<String> args =
						Lists.transform(from.asList(), Tree.<String> asLeafFunction());

				String nonterminal = args.get(0);
				String name = args.get(1);
				List<String> content = args.subList(2, args.size());

				return new Sequence(nonterminal, name, content);
			}
		});
	}

	private static List<Pair<String, String>> tokenTypesFromStringTree(
			List<Tree<String>> struct) {
		return Lists.transform(
				struct,
				new Function<Tree<String>, Pair<String, String>>() {
					@Override
					public Pair<String, String> apply(Tree<String> from) {
						return Pair.of(from.asList().get(0).asLeaf(), from
								.asList()
								.get(1)
								.asLeaf());
					}
				});
	}

	private GrammarModules() {
	}
}
