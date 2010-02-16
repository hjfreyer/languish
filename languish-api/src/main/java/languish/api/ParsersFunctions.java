package languish.api;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.tools.parsing.StringTreeParser;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class ParsersFunctions {

  public static final NativeFunction STRING_TREE_PARSER_FUNCTION =
      new NativeFunction() {

        @Override
        public Tree<Primitive> apply(Tree<Primitive> arg) {
          String strRep = arg.asLeaf().asString();

          return PrimitiveTree.from(StringTreeParser
              .getStringTreeParser()
              .parse(strRep));
        }
      };

  public static final Map<String, NativeFunction> FUNCTION_MAP =
      ImmutableMap.of(
          "native/parsers/string_tree_parser",
          ParsersFunctions.STRING_TREE_PARSER_FUNCTION);

}
