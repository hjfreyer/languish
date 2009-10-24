package languish.base.error;

import languish.base.Tuple;

public class AlreadyReducedError extends RuntimeException {

  public AlreadyReducedError(Tuple t) {
    super("Already reduced: " + t);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 178475778358200797L;

}
