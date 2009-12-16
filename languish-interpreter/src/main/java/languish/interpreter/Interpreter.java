package languish.interpreter;

import java.util.List;

import languish.base.Term;
import languish.base.Terms;
import languish.interpreter.error.DependencyUnavailableError;
import languish.util.PrimitiveTree;

public class Interpreter {

  public static Term reduceModule(Term module, DependencyManager depman)
      throws DependencyUnavailableError {
    String moduleCommand =
        Terms.convertTermToJavaObject(Terms.car(module)).asPrimitive()
            .asString();

    Term moduleArgument = Terms.car(Terms.cdr(module));

    if (moduleCommand.equals("VALUE")) {
      return moduleArgument;
    } else if (moduleCommand.equals("LOAD")) {
      List<PrimitiveTree> depNameList =
          Terms.convertTermToJavaObject(Terms.car(moduleArgument)).asList();
      Term moduleValue = Terms.car(Terms.cdr(moduleArgument));

      Term depList = Term.NULL;

      for (int i = depNameList.size() - 1; i >= 0; i--) {
        String depName = depNameList.get(i).asPrimitive().asString();

        depList = Terms.cons(depman.getResource(depName), depList);
      }

      return Terms.app(moduleValue, depList);
    } else {
      throw new AssertionError();
    }
  }

  private Interpreter() {
  }
}