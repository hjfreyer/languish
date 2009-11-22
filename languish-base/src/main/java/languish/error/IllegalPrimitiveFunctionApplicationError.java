package languish.error;

import languish.base.Term;

public class IllegalPrimitiveFunctionApplicationError extends RuntimeException {

  public IllegalPrimitiveFunctionApplicationError(Term arg) {
    super("Argument is not primitive: " + arg);
  }

  /**
   * 
   */
  private static final long serialVersionUID = -2276755873524353995L;

}
