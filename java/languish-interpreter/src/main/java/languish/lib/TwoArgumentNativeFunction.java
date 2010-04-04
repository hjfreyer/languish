package languish.lib;

import languish.base.NativeFunction;
import languish.base.Primitive;

import com.hjfreyer.util.Tree;

public abstract class TwoArgumentNativeFunction implements NativeFunction {
  @Override
  public final Tree<Primitive> apply(Tree<Primitive> arg) {
    Primitive first = arg.asList().get(0).asLeaf();
    Primitive second = arg.asList().get(1).asLeaf();

    return apply(first, second);
  }

  protected abstract Tree<Primitive> apply(Primitive arg1, Primitive arg2);
}
