package languish.interpreter;

import languish.base.Term;
import languish.base.Terms;
import languish.base.error.AlreadyReducedError;
import languish.interpreter.error.DependencyUnavailableError;

public class Interpreter {

  public static Term reduceModuleCompletely(Term module,
      DependencyManager depman) {
    while (true) {
      String moduleCommand =
          Terms.convertTermToJavaObject(Terms.car(module)).asPrimitive()
              .asString();

      if (moduleCommand.equals("VALUE")) {
        return Terms.car(Terms.cdr(module));
      }

      module = reduceModule(module, depman);
    }
  }

  public static Term reduceModule(Term module, DependencyManager depman)
      throws DependencyUnavailableError {
    String moduleCommand =
        Terms.convertTermToJavaObject(Terms.car(module)).asPrimitive()
            .asString();

    Term moduleArgument = Terms.car(Terms.cdr(module));

    if (moduleCommand.equals("VALUE")) {
      throw new AlreadyReducedError(module);
    } else if (moduleCommand.equals("LOAD")) {
      String depName =
          Terms.convertTermToJavaObject(Terms.car(moduleArgument))
              .asPrimitive().asString();
      Term moduleValue = Terms.car(Terms.cdr(moduleArgument));

      return Terms.app(moduleValue, depman.getResource(depName));
    } else {
      throw new AssertionError();
    }
  }

  private Interpreter() {
  }
}