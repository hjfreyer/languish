package languish.compiler.error;

public class DependencyUnavailableError extends RuntimeException {

	private static final long serialVersionUID = -5018003648586466136L;

	public DependencyUnavailableError() {
		super();
	}

	public DependencyUnavailableError(String message, Throwable cause) {
		super(message, cause);
	}

	public DependencyUnavailableError(String message) {
		super(message);
	}

	public DependencyUnavailableError(Throwable cause) {
		super(cause);
	}

}
