package languish.lib;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.base.Primitives;
import languish.parsing.GrammarModule;
import languish.parsing.GrammarModules;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class GrammarFunctions {

	public static final NativeFunction PARSE_TEXT = new NativeFunction() {
		@Override
		public Tree<Primitive> apply(Tree<Primitive> arg) {
			Tree<Primitive> grammarTree = arg.asList().get(0);
			String text = arg.asList().get(1).asLeaf().asString();

			GrammarModule grammar =
					GrammarModules.moduleFromStringTree(Trees.transform(
							grammarTree,
							Primitives.asString()));

			return Primitives.treeToPrimitiveTree(grammar.getAstParser().parse(text));
		}
	};

	public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
			ImmutableMap.<String, NativeFunction> builder()//
					.put("builtin/parsing/parse_text_given_grammar", PARSE_TEXT)
					.build();
}
