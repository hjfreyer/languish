package languish.parsing.stringparser;

import java.util.List;

import languish.parsing.api.GrammarModule;
import languish.parsing.api.SemanticModule;
import languish.parsing.api.Sequence;
import languish.serialization.StringTreeSerializer;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class StringWithImportParser {
	@SuppressWarnings("unchecked")
	public static final List<Pair<String, String>> TOKEN_PAIRS =
			ImmutableList.of( //
					Pair.of("IMPORT_STATEMENT", "#import"),
					Pair.of(";", ";"),
					Pair.of(",", ","));

	public static final List<Sequence> RULES =
			ImmutableList.of( //
					Sequence.of(
							"StringWithImportParser.COMPILATION_UNIT",
							"StringWithImportParser.COMPILATION_UNIT",
							"IMPORT_DIRECTIVE",
							"CONTENT"),
					Sequence.of(
							"IMPORT_DIRECTIVE",
							"IMPORTS_LIST",
							"IMPORT_STATEMENT",
							"STRING_LIT",
							"IMPORT_TAIL"),
					Sequence.of("IMPORT_DIRECTIVE", "EMPTY_IMPORT"),
					Sequence.of("IMPORT_TAIL", "IMPORT_TAIL_END", ";"),
					Sequence.of(
							"IMPORT_TAIL",
							"IMPORT_TAIL_CONT",
							",",
							"STRING_LIT",
							"IMPORT_TAIL"),
					Sequence.of("CONTENT", "CONTENT", "TREE"));

	public static final GrammarModule STRING_WITH_IMPORT_GRAMMAR =
			StringTreeParser.STRING_TREE_GRAMMAR.extend(new GrammarModule(
					"StringWithImportParser.COMPILATION_UNIT",
					TOKEN_PAIRS,
					ImmutableList.<String> of(),
					RULES));

	private static final//
	ImmutableMap<String, Function<String, Object>> LEAVES =
			ImmutableMap.<String, Function<String, Object>> builder().build();

	private static final//
	ImmutableMap<String, Function<List<Object>, Object>> INODES =
			ImmutableMap.<String, Function<List<Object>, Object>> builder() //
					.put(
							"StringWithImportParser.COMPILATION_UNIT",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									List<String> imports = (List<String>) arg.get(0);
									Tree<String> code = (Tree<String>) arg.get(1);

									return Pair.of(imports, code);
								}
							})
					.put("EMPTY_IMPORT", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return ImmutableList.of();
						}
					})
					.put("IMPORTS_LIST", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							String car = (String) arg.get(1);
							List<String> cdr = (List<String>) arg.get(2);

							List<String> result = Lists.newLinkedList();

							result.add(car);
							result.addAll(cdr);

							return ImmutableList.copyOf(result);
						}
					})
					.put("IMPORT_TAIL_END", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return ImmutableList.of();
						}
					})
					.put("IMPORT_TAIL_CONT", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							String car = (String) arg.get(1);
							List<String> cdr = (List<String>) arg.get(2);

							List<String> result = Lists.newLinkedList();

							result.add(car);
							result.addAll(cdr);

							return ImmutableList.copyOf(result);
						}
					})
					.put("CONTENT", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> ast = (Tree<String>) arg.get(0);

							return StringTreeSerializer.deserialize(ast);
						}
					})
					.build();

	public static final SemanticModule STRING_WITH_IMPORT_SEMANTIC =
			StringTreeParser.STRING_TREE_SEMANTIC.extend(new SemanticModule(
					LEAVES,
					INODES));

	public static Parser<Pair<List<String>, Tree<String>>> getStringWithImportParser() {
		return STRING_WITH_IMPORT_GRAMMAR.getAstParser().map(
				new Map<Tree<String>, Pair<List<String>, Tree<String>>>() {
					@SuppressWarnings("unchecked")
					@Override
					public Pair<List<String>, Tree<String>> map(Tree<String> from) {
						return (Pair<List<String>, Tree<String>>) STRING_WITH_IMPORT_SEMANTIC
								.process(from);
					}
				});
	}
}
