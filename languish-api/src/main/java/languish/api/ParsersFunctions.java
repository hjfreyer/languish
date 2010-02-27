package languish.api;

import java.util.List;
import java.util.Map;

import languish.api.parsing.base.StringWithImportParser;
import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.base.Primitives;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class ParsersFunctions {

	public static final NativeFunction STRING_WITH_IMPORT_PARSER_FUNCTION =
			new NativeFunction() {

				@SuppressWarnings("unchecked")
				@Override
				public Tree<Primitive> apply(Tree<Primitive> arg) {
					String strRep = arg.asLeaf().asString();

					Pair<List<String>, Tree<String>> parsed =
							StringWithImportParser.getStringWithImportParser().parse(strRep);

					Tree<String> importTree = Tree.copyOf(parsed.getFirst());
					Tree<String> codeTree = parsed.getSecond();

					return Primitives.treeToPrimitiveTree(Tree
							.inode(importTree, codeTree));
				}
			};

	public static final Map<String, NativeFunction> FUNCTION_MAP =
			ImmutableMap.of(
					"native/parsers/string_with_import_parser",
					STRING_WITH_IMPORT_PARSER_FUNCTION);

}
