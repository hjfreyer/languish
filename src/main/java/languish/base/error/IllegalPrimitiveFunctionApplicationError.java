package languish.base.error;

import languish.base.Tuple;

public class IllegalPrimitiveFunctionApplicationError extends RuntimeException {

  public IllegalPrimitiveFunctionApplicationError(Tuple arg) {
    super("Argument is not primitive: " + arg);
  }

  /**
   * 
   */
  private static final long serialVersionUID = -2276755873524353995L;

}
