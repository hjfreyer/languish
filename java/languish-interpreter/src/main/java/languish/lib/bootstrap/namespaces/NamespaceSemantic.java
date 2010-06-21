package languish.lib.bootstrap.namespaces;

import java.util.List;
import java.util.Map;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.bootstrap.lambdaplus.LambdaPlusSemantic;
import languish.parsing.api.SemanticModule;
import languish.parsing.api.SemanticModules;
import languish.serialization.StringTreeSerializer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class NamespaceSemantic {

	// public static class Selector {
	// enum Type {
	// GET_ATTR, CALL_METHOD
	// };
	//
	// Type type;
	// Tree<String> arg;
	// }

	public static final Map<String, Function<String, Object>> IDENTITY_LEAF_RULES =
			SemanticModules.getIdentityLeafRules(ImmutableList.of(
					"Namespaces.NATIVE_KEYWORD",
					"DOT",
					"IDENT",
					"(",
					")"));

	public static final Map<String, Function<String, Object>> LEAF_RULES =
			ImmutableMap.<String, Function<String, Object>> builder() //
					.putAll(IDENTITY_LEAF_RULES)
					.build();

	public static final Map<String, Function<List<Object>, Object>> EMPTY_RULES =
			SemanticModules.getEmptyListLeafRules(ImmutableList
					.of("Namespaces.EXPRESSION.SELECTORS.Empty"));

	public static final Map<String, Function<List<Object>, Object>> INODE_RULES =
			ImmutableMap.<String, Function<List<Object>, Object>> builder() //
					.putAll(EMPTY_RULES)
					.put(
							"Namespaces.COMPILATION_UNIT",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									List<String> imports = (List<String>) arg.get(0);
									Term term = (Term) arg.get(1);

									return Tree.inode(
											Tree.<String> copyOf(imports),
											StringTreeSerializer.serialize(term));
								}
							})
					.put("Namespaces.NATIVE_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							return arg.get(2);
						}
					})
					.put(
							"Namespaces.EXPRESSION.SELECTORS.CALL_METHOD",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									Term call_arg = (Term) arg.get(1);
									List<Term> rest = (List<Term>) arg.get(3);

									return ImmutableList
											.builder()
											.add(call_arg)
											.addAll(rest)
											.build();
								}
							})
					.put("Namespaces.EXPRESSION", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> args) {
							Term base = (Term) args.get(0);
							List<Term> call_args = (List<Term>) args.get(1);

							for (Term arg : call_args) {
								base = callWithArg(base, arg);
							}

							return base;
						}
					})
					.build();

	public static final Term callWithArg(Term term, Term arg) {
		return Terms.app(Terms.car(term), arg);
	}

	public static final SemanticModule LAMBDA_PLUS_SEMANTIC =
			LambdaPlusSemantic.LAMBDA_PLUS_SEMANTIC.extend(new SemanticModule(
					LEAF_RULES,
					INODE_RULES));

}
