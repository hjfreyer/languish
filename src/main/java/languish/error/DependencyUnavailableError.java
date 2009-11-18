package languish.error;

public class DependencyUnavailableError extends RuntimeException {

  private static final long serialVersionUID = 5685167716829495363L;

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
