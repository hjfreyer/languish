package languish.api;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.tools.parsing.TermParser;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class ParsersFunctions {

  public static final Map<String, NativeFunction> FUNCTION_MAP =
      ImmutableMap.of();

  public static final NativeFunction TERM_PARSER_FUNCTION =
      new NativeFunction() {

        @Override
        public Tree<Primitive> apply(Tree<Primitive> arg) {
          String strRep = arg.asLeaf().asString();

          return PrimitiveTree.from(TermParser.TERM_GRAMMAR
              .getAstParser()
              .parse(strRep));
        }
      };
}
