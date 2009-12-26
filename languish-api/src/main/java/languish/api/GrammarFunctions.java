package languish.api;

import languish.base.NativeFunction;
import languish.base.Primitive;

import com.hjfreyer.util.Tree;

public class GrammarFunctions {
  public static final NativeFunction PARSE_TEXT = new NativeFunction() {
    @Override
    public Tree<Primitive> apply(Tree<Primitive> arg) {
      Tree<Primitive> grammar = arg.asList().get(0);
      String text = arg.asList().get(1).asLeaf().asString();

      return null;
    }

  };
}
