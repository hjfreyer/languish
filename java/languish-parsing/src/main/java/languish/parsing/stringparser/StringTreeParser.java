package languish.parsing.stringparser;

import java.util.List;

import languish.parsing.api.GrammarModule;
import languish.parsing.api.SemanticModule;
import languish.parsing.api.Sequence;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class StringTreeParser {

	@SuppressWarnings("unchecked")
	public static final List<Pair<String, String>> TOKEN_PAIRS =
			ImmutableList.of( //
					Pair.of("[", "\\["),
					Pair.of("]", "\\]"),
					Pair.of("STRING_LIT", "\"(((\\\\.)|[^\"\\\\])*)\""));

	public static final List<String> DELIM = ImmutableList.of("(\\s|//[^\n]*)*");

	public static final List<Sequence> RULES = ImmutableList.of( //
			Sequence.of("TREE", "LEAF", "STRING_LIT"),
			Sequence.of("TREE", "INODE", "[", "TREE_TAIL"),
			Sequence.of("TREE_TAIL", "TREE_END", "]"),
			Sequence.of("TREE_TAIL", "TREE_CONT", "TREE", "TREE_TAIL"));

	public static final GrammarModule STRING_TREE_GRAMMAR =
			new GrammarModule(
					"TREE",
					StringTreeParser.TOKEN_PAIRS,
					StringTreeParser.DELIM,
					StringTreeParser.RULES);

	private static final//
	ImmutableMap<String, Function<String, Object>> LEAVES =
			ImmutableMap.<String, Function<String, Object>> builder().put(
					"STRING_LIT",
					new Function<String, Object>() {
						@Override
						public Object apply(String arg) {
							return arg.substring(1, arg.length() - 1) //
									.replaceAll("\\\\(.)", "\\1");
						}
					}).build();

	private static final//
	ImmutableMap<String, Function<List<Object>, Object>> INODES =
			ImmutableMap.<String, Function<List<Object>, Object>> builder() //
					.put("LEAF", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return Tree.leaf(arg.get(0));
						}
					})
					.put("INODE", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							return Tree.inode((List<Tree<Object>>) arg.get(1));
						}
					})
					.put("TREE_END", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return ImmutableList.of();
						}
					})
					.put("TREE_CONT", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> car = (Tree<String>) arg.get(0);
							List<Tree<String>> cdr = (List<Tree<String>>) arg.get(1);

							List<Tree<String>> result = Lists.newLinkedList();

							result.add(car);
							result.addAll(cdr);

							return ImmutableList.copyOf(result);
						}
					})
					.build();

	public static final SemanticModule STRING_TREE_SEMANTIC =
			new SemanticModule(StringTreeParser.LEAVES, StringTreeParser.INODES);

	public static Parser<Tree<String>> getStringTreeParser() {
		return STRING_TREE_GRAMMAR.getAstParser().map(
				new Map<Tree<String>, Tree<String>>() {
					@SuppressWarnings("unchecked")
					@Override
					public Tree<String> map(Tree<String> from) {
						return (Tree<String>) StringTreeParser.STRING_TREE_SEMANTIC
								.process(from);
					}
				});
	}
	// public static final Parser<Tree<String>> STRING_TREE_PARSER = Parsers.
}
