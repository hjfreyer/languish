package languish.error;

import languish.lambda.Term;

public class AlreadyReducedError extends RuntimeException {

  public AlreadyReducedError(Term t) {
    super("Already reduced: " + t);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 178475778358200797L;

}
