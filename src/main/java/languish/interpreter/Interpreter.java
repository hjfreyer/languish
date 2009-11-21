package languish.interpreter;

import java.util.List;

import languish.error.DependencyUnavailableError;
import languish.lambda.Term;
import languish.util.JavaWrapper;
import languish.util.Lambda;

public class Interpreter {

  public static Term reduceModule(Term module, DependencyManager depman)
      throws DependencyUnavailableError {
    String moduleCommand =
        Lambda.convertTermToJavaObject(Lambda.car(module)).asString();

    Term moduleArgument = Lambda.cdr(module);

    if (moduleCommand.equals("VALUE")) {
      return moduleArgument;
    } else if (moduleCommand.equals("LOAD")) {
      List<JavaWrapper> depNameList =
          Lambda.convertTermToJavaObject(Lambda.car(moduleArgument)).asList();
      Term moduleValue = Lambda.cdr(moduleArgument);

      Term depList = Term.NULL;

      for (int i = depNameList.size() - 1; i >= 0; i--) {
        String depName = depNameList.get(i).asString();

        depList = Lambda.cons(depman.getResource(depName), depList);
      }

      return Lambda.app(moduleValue, depList);
    } else {
      throw new AssertionError();
    }
  }

  private Interpreter() {
  }
}