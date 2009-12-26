package languish.api;

import languish.base.NativeFunction;
import languish.util.PrimitiveTree;

public class GrammarFunctions {
  public static final NativeFunction PARSE_TEXT = new NativeFunction() {
    @Override
    public PrimitiveTree apply(PrimitiveTree arg) {
      PrimitiveTree grammar = arg.asList().get(0);
      String text = arg.asList().get(1).asPrimitive().asString();

      return null;
    }

  };
}
