package languish.interpreter;

import java.util.List;

import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Interpreter {
  //
  // public static Term reduceModuleCompletely(Term module,
  // DependencyManager depman) {
  // while (true) {
  // String moduleCommand =
  // Terms.convertTermToJavaObject(Terms.car(module)).asLeaf().asString();
  //
  // if (moduleCommand.equals("VALUE")) {
  // return Terms.car(Terms.cdr(module));
  // }
  //
  // module = reduceModule(module, depman);
  // }
  // }
  //
  // public static Term reduceModule(Term module, DependencyManager depman)
  // throws DependencyUnavailableError {
  // String moduleCommand =
  // Terms.convertTermToJavaObject(Terms.car(module)).asLeaf().asString();
  //
  // Term moduleArgument = Terms.car(Terms.cdr(module));
  //
  // if (moduleCommand.equals("VALUE")) {
  // throw new AlreadyReducedError(module);
  // } else if (moduleCommand.equals("REDUCE_AND_APPLY")) {
  // Term subModule = Terms.car(moduleArgument);
  // Term subFunction = Terms.car(Terms.cdr(moduleArgument));
  //
  // String subModuleCommand =
  // Terms.convertTermToJavaObject(Terms.car(subModule)).asLeaf()
  // .asString();
  // Term subModuleArgument = Terms.car(Terms.cdr(subModule));
  //
  // if (subModuleCommand.equals("VALUE")) {
  // return Terms.app(subFunction, subModuleArgument);
  // }
  //
  // Term reduced = reduceModule(subModule, depman);
  //
  // return Modules.reduceAndApply(reduced, subFunction);
  // } else if (moduleCommand.equals("LOAD")) {
  // String depName =
  // Terms.convertTermToJavaObject(Terms.car(moduleArgument)).asLeaf()
  // .asString();
  // Term depValue = depman.getResource(depName);
  //
  // Term moduleValue = Terms.car(Terms.cdr(moduleArgument));
  // return Modules.reduceAndApply(depValue, moduleValue);
  // } else {
  // throw new AssertionError();
  // }
  // }

  public static Term bindDependencies(Term module, DependencyManager depman) {
    Term depNamesListTerm = Terms.car(module);
    Term moduleAstTerm = Terms.car(Terms.cdr(module));

    List<Term> deps = Lists.newLinkedList();
    for (Tree<Primitive> depNameObj : Terms.convertTermToJavaObject(
        depNamesListTerm).asList()) {
      String depName = depNameObj.asLeaf().asString();

      deps.add(depman.getResource(depName));
    }

    Term depsListTerm = Term.NULL;
    for (int i = deps.size() - 1; i >= 0; i--) {
      depsListTerm = Terms.cons(deps.get(i), depsListTerm);
    }

    // The module itself
    Tree<Primitive> modulePrimAst =
        Terms.convertTermToJavaObject(moduleAstTerm);
    Tree<String> moduleAst =
        Trees.transform(modulePrimAst, new Function<Primitive, String>() {
          @Override
          public String apply(Primitive from) {
            return from.asString();
          }
        });

    Term compiledModule = Terms.compileAstToTerm(moduleAst);
    return Terms.app(compiledModule, depsListTerm);
  }

  private Interpreter() {
  }
}