package languish.lib.bootstrap.namespaces;

import java.util.List;
import java.util.Map;

import languish.base.Terms;
import languish.parsing.api.SemanticModule;
import languish.serialization.StringTreeSerializer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;

public class NamespaceSemantic {

	public static final Map<String, Function<String, Object>> IDENTITY_LEAF_RULES =
			SemanticModule.getIdentityLeafRules(ImmutableList.of(
					"ABS",
					"APP",
					"CONS",
					"CAR",
					"CDR",
					"EQUALS",
					"REF",
					"NULL",
					"IMPORT_DIRECTIVE",
					"IMPORT_IDENT",
					"INTEGER_LIT",
					",",
					";;"));

	public static final Map<String, Function<String, Object>> LEAF_RULES =
			ImmutableMap.<String, Function<String, Object>> builder() //
					.putAll(IDENTITY_LEAF_RULES)
					.put("STRING_LIT", new Function<String, Object>() {
						@Override
						public Object apply(String arg) {
							return arg.substring(1, arg.length() - 1);
						}
					})
					.build();

	public static final Map<String, Function<List<Object>, Object>> INODE_RULES =
			ImmutableMap.<String, Function<List<Object>, Object>> builder() //
					.put(
							"LambdaPlusGrammar.COMPILATION_UNIT",
							new Function<List<Object>, Object>() {
								@SuppressWarnings("unchecked")
								@Override
								public Object apply(List<Object> arg) {
									List<String> imports = (List<String>) arg.get(0);
									Tree<String> code = (Tree<String>) arg.get(1);

									return Tree.inode(Tree.<String> copyOf(imports), code);
								}
							})
					.put("EMPTY_IMPORT", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return ImmutableList.of();
						}
					})
					.put("IMPORT_STATEMENT", new Function<List<Object>, Object>() {
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
					.put("ABS_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("ABS");
							Tree<String> arg1 = (Tree<String>) arg.get(2);
							Tree<String> arg2 = StringTreeSerializer.serialize(Terms.NULL);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("APP_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("APP");
							Tree<String> arg1 = (Tree<String>) arg.get(2);
							Tree<String> arg2 = (Tree<String>) arg.get(3);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("CONS_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> cons_func =
									StringTreeSerializer.serialize(Terms.CONS);

							Tree<String> app = Tree.leaf("APP");
							Tree<String> arg1 = (Tree<String>) arg.get(2);
							Tree<String> arg2 = (Tree<String>) arg.get(3);

							return Tree.inode(app, Tree.inode(app, cons_func, arg1), arg2);
						}
					})
					.put("CAR_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> car_func = StringTreeSerializer.serialize(Terms.CAR);
							Tree<String> app = Tree.leaf("APP");
							Tree<String> cons = (Tree<String>) arg.get(2);

							return Tree.inode(app, car_func, cons);
						}
					})
					.put("CDR_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> cdr_func = StringTreeSerializer.serialize(Terms.CDR);

							Tree<String> app = Tree.leaf("APP");
							Tree<String> cons = (Tree<String>) arg.get(2);

							return Tree.inode(app, cdr_func, cons);
						}
					})
					.put("IS_PRIMITIVE_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("IS_PRIMITIVE");
							Tree<String> arg1 = (Tree<String>) arg.get(2);
							Tree<String> arg2 = StringTreeSerializer.serialize(Terms.NULL);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("NATIVE_APPLY_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("NATIVE_APPLY");
							Tree<String> arg1 = Tree.leaf((String) arg.get(2));
							Tree<String> arg2 = (Tree<String>) arg.get(3);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("REF_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("REF");
							Tree<String> arg1 = Tree.leaf((String) arg.get(2));
							Tree<String> arg2 = StringTreeSerializer.serialize(Terms.NULL);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("NULL_TERM", new Function<List<Object>, Object>() {
						@Override
						public Object apply(List<Object> arg) {
							return StringTreeSerializer.serialize(Terms.NULL);
						}
					})
					.put("STRING_LIT_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("PRIMITIVE");
							Tree<String> arg1 = Tree.leaf("s" + arg.get(0));
							Tree<String> arg2 = StringTreeSerializer.serialize(Terms.NULL);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.put("INT_LIT_TERM", new Function<List<Object>, Object>() {
						@SuppressWarnings("unchecked")
						@Override
						public Object apply(List<Object> arg) {
							Tree<String> op = Tree.leaf("PRIMITIVE");
							Tree<String> arg1 = Tree.leaf("i" + arg.get(0));
							Tree<String> arg2 = StringTreeSerializer.serialize(Terms.NULL);

							return Tree.inode(op, arg1, arg2);
						}
					})
					.build();

	public static final SemanticModule LAMBDA_PLUS_SEMANTIC =
			new SemanticModule(LEAF_RULES, INODE_RULES);

}
