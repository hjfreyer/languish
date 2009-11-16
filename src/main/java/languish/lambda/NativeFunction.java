package languish.lambda;

import languish.util.JavaWrapper;

public interface NativeFunction {

  public JavaWrapper apply(JavaWrapper arg);

}
