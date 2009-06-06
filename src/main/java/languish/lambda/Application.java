package languish.lambda;

import languish.lambda.error.IllegalApplicationError;
import languish.lambda.error.IllegalFreeVariableError;

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
  public Expression reduceOnce() {
    switch (function.getType()) {
    case WRAPPER:
    case REFERENCE:
      throw new IllegalApplicationError(function
          + " cannot be reduced to abstraction");

    case APPLICATION:
      return Application.of(function.reduceOnce(), argument);

    case ABSTRACTION:
      Abstraction funcAbs = (Abstraction) function;

      return funcAbs.getExpression().replaceAllReferencesToParam(1, argument);

    case NATIVE_FUNC:
      NativeFunction natFunc = (NativeFunction) function;

      switch (argument.getType()) {
      case APPLICATION:
        return Application.of(natFunc, argument.reduceOnce());

      case NATIVE_FUNC:
      case REFERENCE:
      case ABSTRACTION:
        throw new IllegalFreeVariableError();

      case WRAPPER:
        Wrapper wrapper = (Wrapper) argument;

        return natFunc.apply(wrapper.getContents());
      default:
        throw new AssertionError();
      }
    default:
      throw new AssertionError();
    }
  }

  @Override
  public Expression replaceAllReferencesToParam(int id, Expression with) {

    Expression function = this.function.replaceAllReferencesToParam(id, with);
    Expression argument = this.argument.replaceAllReferencesToParam(id, with);

    return Application.of(function, argument);
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
