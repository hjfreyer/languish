package languish.base.error;

import languish.base.Term;

public class InvalidApplicationException extends RuntimeException {

	public InvalidApplicationException(Term t) {
		super("Cannont perform application: " + t);
	}

	/**
	   * 
	   */
	private static final long serialVersionUID = 178475778358200797L;

}
