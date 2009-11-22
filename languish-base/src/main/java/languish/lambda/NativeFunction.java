package languish.lambda;

import languish.util.PrimitiveTree;

public interface NativeFunction {

  public PrimitiveTree apply(PrimitiveTree arg);

}
