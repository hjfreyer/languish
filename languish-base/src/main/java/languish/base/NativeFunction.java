package languish.base;

import languish.util.PrimitiveTree;

public interface NativeFunction {

  public PrimitiveTree apply(PrimitiveTree arg);

}
