package languish.lambda;

public final class Application extends Expression {

  private final Expression function;
  private final Expression argument;

  private Application(Expression function, Expression argument) {
    this.function = function;
    this.argument = argument;
  }

  public static Application of(Expression function, Expression argument) {
    return new Application(function, argument);
  }

  @Override
  public Type getType() {
    return Type.APPLICATION;
  }

  @Override
  public String toString() {
    return "(APP " + function + " " + argument + ")";
  }

  public Expression getFunction() {
    return function;
  }

  public Expression getArgument() {
    return argument;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((argument == null) ? 0 : argument.hashCode());
    result = prime * result + ((function == null) ? 0 : function.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Application other = (Application) obj;
    if (argument == null) {
      if (other.argument != null) {
        return false;
      }
    } else if (!argument.equals(other.argument)) {
      return false;
    }
    if (function == null) {
      if (other.function != null) {
        return false;
      }
    } else if (!function.equals(other.function)) {
      return false;
    }
    return true;
  }

}
