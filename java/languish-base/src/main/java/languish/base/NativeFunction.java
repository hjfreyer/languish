package languish.base;

import com.hjfreyer.util.Tree;

public interface NativeFunction {

  public Tree<Primitive> apply(Tree<Primitive> arg);

}
