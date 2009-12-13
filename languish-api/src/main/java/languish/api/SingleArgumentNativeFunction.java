package languish.api;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.util.PrimitiveTree;

public abstract class SingleArgumentNativeFunction implements NativeFunction {

  @Override
  public final PrimitiveTree apply(PrimitiveTree arg) {
    return apply(arg.asPrimitive());
  }

  public abstract PrimitiveTree apply(Primitive arg);
}
