package languish.compiler.error;

public class CompilationError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompilationError() {
		super();
	}

	public CompilationError(String message, Throwable cause) {
		super(message, cause);
	}

	public CompilationError(String message) {
		super(message);
	}

	public CompilationError(Throwable cause) {
		super(cause);
	}

}
