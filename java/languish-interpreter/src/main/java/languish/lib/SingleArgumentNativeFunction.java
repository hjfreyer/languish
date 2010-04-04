package languish.lib;

import languish.base.NativeFunction;
import languish.base.Primitive;

import com.hjfreyer.util.Tree;

public abstract class SingleArgumentNativeFunction implements NativeFunction {

  @Override
  public final Tree<Primitive> apply(Tree<Primitive> arg) {
    return apply(arg.asLeaf());
  }

  public abstract Tree<Primitive> apply(Primitive arg);
}
