package languish.interpreter;

import java.util.List;
import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

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

  public static Term loadAndInterpret(String depName, DependencyManager depman,
      Map<String, NativeFunction> nativeFunctions) {
    return Interpreter.interpret(depman.getResource(depName), depman,
        nativeFunctions);
  }

  public static Term interpret(String doc, DependencyManager depman,
      Map<String, NativeFunction> nativeFunctions) {
    if (doc.startsWith("#nativefunction:")) {
      String functionName = doc.replace("#nativefunction:", "");
      NativeFunction func = nativeFunctions.get(functionName);
      return Terms.abs(Terms.nativeApply(func, Terms.ref(1)));
    }
    Pair<String, String> parserAndProgram = BaseParser.getParserAndProgram(doc);
    Term parser = Interpreter.loadAndInterpret(parserAndProgram.getFirst(),
        depman, nativeFunctions);
    Term module = Terms
        .app(parser, Terms.primObj(parserAndProgram.getSecond()));
    Pair<List<String>, Term> depsAndTerm = Interpreter.getDepsAndTerm(module);
    return Interpreter.bindDeps(depsAndTerm.getSecond(),
        depsAndTerm.getFirst(), depman, nativeFunctions);
  }

  public static Term bindDeps(Term module, List<String> depNames,
      DependencyManager depman, Map<String, NativeFunction> nativeFunctions) {
    Term depList = Term.NULL;
    for (int i = depNames.size() - 1; i >= 0; i--) {
      Term dep = Interpreter.loadAndInterpret(depNames.get(i), depman,
          nativeFunctions);
      depList = Terms.cons(dep, depList);
    }
    return Terms.app(module, depList);
  }

  public static Pair<List<String>, Term> getDepsAndTerm(Term module) {
    Term depListTerm = Terms.car(module);
    Term moduleAst = Terms.car(Terms.cdr(module));
    Tree<Primitive> depListTree = Terms.convertTermToJavaObject(depListTerm);
    List<String> depList = Lists.transform(depListTree.asList(),
        new Function<Tree<Primitive>, String>() {
          @Override
          public String apply(Tree<Primitive> from) {
            return from.asLeaf().asString();
          }
        });
    Term moduleBody = Terms.compileTermAstToTerm(moduleAst);
    return Pair.of(depList, moduleBody);
  }

  private Interpreter() {
  }
}