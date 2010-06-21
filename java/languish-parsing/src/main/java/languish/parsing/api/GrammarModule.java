package languish.parsing.api;

import java.util.HashMap;
import java.util.List;

import languish.parsing.error.InternalParsingError;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Tokens;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.pattern.Patterns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class GrammarModule {
	private final String rootRule;
	private final List<Pair<String, String>> tokenTypes;
	private final List<String> ignored;
	private final List<Sequence> rules;

	public GrammarModule(
			String rootRule,
			List<Pair<String, String>> tokenTypes,
			List<String> ignored,
			List<Sequence> rules) {
		super();
		this.rootRule = rootRule;
		this.tokenTypes = tokenTypes;
		this.ignored = ignored;
		this.rules = rules;
	}

	public Parser<Tree<String>> getAstParser() {
		return getTokenLevelParser(
				rootRule,
				Lists.transform(tokenTypes, Pair.<String, String> first()),
				rules).from(getTokenizer(tokenTypes), getDelimiterParser(ignored));
	}

	public GrammarModule extend(GrammarModule other) {
		String rootRule = other.rootRule;
		List<Pair<String, String>> tokenTypes = ImmutableList.copyOf( //
				Iterables.concat(this.tokenTypes, other.tokenTypes));
		List<String> ignored =
				ImmutableList.copyOf(Iterables.concat(this.ignored, other.ignored));
		List<Sequence> rules =
				ImmutableList.copyOf(Iterables.concat(this.rules, other.rules));

		return new GrammarModule(rootRule, tokenTypes, ignored, rules);
	}

	static Parser<Fragment> getTokenizer(List<Pair<String, String>> tokenTypes) {
		Parser<Fragment> lexer = Parsers.never();

		for (Pair<String, String> tokenType : tokenTypes) {
			final String tag = tokenType.getFirst().intern();
			final String regex = tokenType.getSecond();

			Parser<Fragment> token =
					Scanners.pattern(Patterns.regex(regex), tag).source().map(
							new Map<String, Fragment>() {
								public Fragment map(String from) {
									return Tokens.fragment(from, tag);
								}
							});

			lexer = lexer.or(token);
		}

		return lexer;
	}

	static Parser<Void> getDelimiterParser(List<String> ignored) {
		Parser<Void> delim = Parsers.never();

		for (String ignoredRegex : ignored) {
			Parser<Void> ignoredParser =
					Scanners.pattern(Patterns.regex(ignoredRegex), "IGNORED");

			delim = delim.or(ignoredParser);
		}

		return delim;
	}

	static Parser<Tree<String>> getTokenLevelParser(
			String rootRule,
			List<String> tokenNames,
			List<Sequence> rules) {

		Multimap<String, Sequence> rulesMap =
				LinkedListMultimap.<String, Sequence> create();
		for (Sequence rule : rules) {
			rulesMap.put(rule.getNonterminal(), rule);
		}

		HashMap<String, Parser<Tree<String>>> nontermParsers =
				new HashMap<String, Parser<Tree<String>>>();
		HashMap<String, Parser.Reference<Tree<String>>> parserRefs =
				new HashMap<String, Parser.Reference<Tree<String>>>();

		for (String type : rulesMap.keySet()) {
			nontermParsers.put(type, Parsers.<Tree<String>> never());
			parserRefs.put(type, Parser.<Tree<String>> newReference());
		}

		for (final String tokenName : tokenNames) {
			nontermParsers.put(tokenName, Terminals.fragment(tokenName.intern()).map(
					new Map<String, Tree<String>>() {

						@SuppressWarnings("unchecked")
						public Tree<String> map(String from) {
							return Tree.inode(Tree.leaf(tokenName), Tree.leaf(from));
						}
					}));

			parserRefs.put(tokenName, Parser.<Tree<String>> newReference());
		}

		for (String type : rulesMap.keySet()) {
			for (final Sequence production : rulesMap.get(type)) {
				Parser<Tree<String>> productionParser = production.toParser(parserRefs);
				nontermParsers.put(type, nontermParsers.get(type).or(productionParser));
			}
		}

		for (String type : nontermParsers.keySet()) {
			parserRefs.get(type).set(nontermParsers.get(type));
		}

		if (!nontermParsers.containsKey(rootRule)) {
			throw new InternalParsingError("rootRule not a rule");
		}

		return nontermParsers.get(rootRule);
	}

	public List<Pair<String, String>> getTokenTypes() {
		return tokenTypes;
	}

	public List<String> getIgnored() {
		return ignored;
	}

	public List<Sequence> getRules() {
		return rules;
	}

	public String getRootRule() {
		return rootRule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ignored == null) ? 0 : ignored.hashCode());
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
		result =
				prime * result + ((tokenTypes == null) ? 0 : tokenTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrammarModule other = (GrammarModule) obj;
		if (ignored == null) {
			if (other.ignored != null)
				return false;
		} else if (!ignored.equals(other.ignored))
			return false;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		if (tokenTypes == null) {
			if (other.tokenTypes != null)
				return false;
		} else if (!tokenTypes.equals(other.tokenTypes))
			return false;
		return true;
	}

}
