package languish.lib.bootstrap.namespaces;

import static languish.base.Terms.*;

import java.util.List;
import java.util.Map;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.bootstrap.lambdaplus.LambdaPlusSemantic;
import languish.parsing.api.SemanticModule;
import languish.parsing.api.SemanticModules;
import languish.serialization.StringTreeSerializer;
import languish.util.PrimitiveTree;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class NamespaceSemantic {

	public static class Selector {
		enum Type {
			FUNCTION_CALL
		};

		Type type;
		List<Pair<String, Tree<String>>> args;
	}

	public static final Map<String, Function<String, Object>> IDENTITY_LEAF_RULES =
			SemanticModules.getIdentityLeafRules(ImmutableList.of(
					"Namespaces.NATIVE_KEYWORD",
					"DOT",
					"IDENT",
					"(",
					")",
					"="));

	public static final Map<String, Function<String, Object>> LEAF_RULES =
			ImmutableMap.<String, Function<String, Object>> builder() //
					.putAll(IDENTITY_LEAF_RULES)
					.build();

	public static final Map<String, Function<List<Object>, Object>> EMPTY_RULES =
			SemanticModules.getEmptyListLeafRules(ImmutableList
					.of("Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST.EMPTY"));

	public static final Map<String, Function<List<Object>, Object>> LIST_RULES =
			SemanticModules.getListRules(ImmutableList.of(
					"Namespaces.SELECTOR_LIST",
					"Namespaces.IMPORT_LIST"));

	public static final Map<String, Function<List<Object>, Object>> FENCEPOST_RULES =
			SemanticModules.getFencePostRules(ImmutableList.of(
					"QUALIFIED_IDENTIFIER",
					"Namespaces.SELECTOR.FUNCTION_CALL.ARG_LIST"));

	public static final Map<String, Function<List<Object>, Object>> INODE_RULES =
			ImmutableMap.<String, Function<List<Object>, Object>> builder() //
					.putAll(EMPTY_RULES)
					.putAll(LIST_RULES)
					.putAll(FENCEPOST_RULES)
					.put(
							"Namespaces.COMPILATION_UNIT",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									List<String> imports = (List<String>) arg.get(0);
									Tree<String> ast = (Tree<String>) arg.get(1);

									Term import_names_term = Terms.fromJavaObject(imports);
									Term ast_term =
											Terms.fromPrimitiveTree(PrimitiveTree.fromTree(ast));

									System.out.println(ast);

									imports =
											ImmutableList
													.<String> builder()
													.add("parsers/namespace/semantic")
													.addAll(
															Lists.transform(
																	imports,
																	new Function<String, String>() {
																		@Override
																		public String apply(String from) {
																			return from.replace('.', '/');
																		}
																	}))
													.build();

									Term module = abs( // Imports
											app(app(app( //
													car(ref(1)), // Semantic
													import_names_term), // Import names
													cdr(ref(1))), // Actual imports
													ast_term) // Program AST
											);

									return Tree.inode(
											Tree.<String> copyOf(imports),
											StringTreeSerializer.serialize(module));
								}
							})
					// .put("Namespaces.IMPORT_LIST", new Function<List<Object>, Object>()
					// {
					// @SuppressWarnings("unchecked")
					// @Override
					// public Object apply(List<Object> arg) {
					// String imprt = (String) arg.get(1);
					// List<String> other_imports = (List<String>) arg.get(3);
					//
					// return ImmutableList
					// .builder()
					// .add(imprt)
					// .addAll(other_imports)
					// .build();
					// }
					// })
					// .put("QUALIFIED_IDENTIFIER", new Function<List<Object>, Object>() {
					// @SuppressWarnings("unchecked")
					// @Override
					// public Object apply(List<Object> arg) {
					// String first = (String) arg.get(0);
					// List<String> rest = (List<String>) arg.get(1);
					//
					// return first + '.' + Joiner.on('.').join(rest);
					// }
					// })
					// .put(
					// "QUALIFIED_IDENTIFIER.TAIL",
					// new Function<List<Object>, Object>() {
					// @SuppressWarnings("unchecked")
					// @Override
					// public Object apply(List<Object> arg) {
					// String ident = (String) arg.get(1);
					// List<String> rest = (List<String>) arg.get(2);
					//
					// return ImmutableList
					// .builder()
					// .add(ident)
					// .addAll(rest)
					// .build();
					// }
					// })

					.put("Namespaces.EXPRESSION", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> args) {
							Tree<String> base = (Tree<String>) args.get(0);
							List<Selector> selectors = (List<Selector>) args.get(1);

							for (Selector selector : selectors) {
								switch (selector.type) {
								case FUNCTION_CALL: {
									List<Tree<String>> argPairs = Lists.transform( //
											selector.args,
											new Function<Pair<String, Tree<String>>, Tree<String>>() {
												@Override
												public Tree<String> apply(
														Pair<String, Tree<String>> from) {
													String name = from.getFirst();
													Tree<String> value = from.getSecond();

													Tree<String> nameTree =
															Tree.inode(
																	Tree.leaf("IDENTIFIER"),
																	Tree.leaf(name));
													return Tree.inode(
															Tree.leaf("ARG_PAIR"),
															Tree.inode(nameTree, value));
												}
											});

									Tree<String> argPairsTree =
											Tree.inode(Tree.leaf("ARG_PAIRS"), Tree.inode(argPairs));

									base =
											Tree.inode(
													Tree.leaf("FUNCTION_CALL"),
													Tree.inode(base, argPairsTree));
									break;
								}
								default:
									throw new AssertionError();
								}
							}

							return base;
						}
					})
					.put(
							"Namespaces.EXPRESSION.BASE.STRING_LITERAL",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> args) {
									String str = (String) args.get(0);

									return Tree.inode(Tree.leaf("STRING_LITERAL"), Tree.leaf(str));
								}
							})
					.put(
							"Namespaces.EXPRESSION.BASE.IDENTIFIER_GET",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> args) {
									String id = (String) args.get(0);

									return Tree.inode(
											Tree.leaf("IDENTIFIER_GET"),
											Tree.inode(Tree.inode(
													Tree.leaf("IDENTIFIER"),
													Tree.leaf(id))));
								}
							})
					// .put(
					// "Namespaces.SELECTOR_LIST",
					// new Function<List<Object>, Object>() {
					// @SuppressWarnings("unchecked")
					// @Override
					// public Object apply(List<Object> arg) {
					// Selector car = (Selector) arg.get(0);
					// List<Selector> cdr = (List<Selector>) arg.get(1);
					//
					// return ImmutableList.builder().add(car).addAll(cdr).build();
					// }
					// })
					.put(
							"Namespaces.SELECTOR.FUNCTION_CALL",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									Selector selector = new Selector();

									selector.type = Selector.Type.FUNCTION_CALL;
									selector.args = (List<Pair<String, Tree<String>>>) arg.get(1);

									return selector;
								}
							})
					.put(
							"Namespaces.SELECTOR.FUNCTION_CALL.ARG",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> args) {
									String name = (String) args.get(0);
									Tree<String> value = (Tree<String>) args.get(2);
									return Pair.of(name, value);
								}
							})
					.build();

	public static final SemanticModule LAMBDA_PLUS_SEMANTIC =
			LambdaPlusSemantic.LAMBDA_PLUS_SEMANTIC.extend(new SemanticModule(
					LEAF_RULES,
					INODE_RULES));

}
