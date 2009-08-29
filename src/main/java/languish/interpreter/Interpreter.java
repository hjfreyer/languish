package languish.interpreter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import languish.base.Lambda;
import languish.base.Tuple;
import languish.base.Util;
import languish.primitives.LSymbol;

import com.hjfreyer.util.Pair;

public class Interpreter {

  private Interpreter() {}

  @SuppressWarnings("unchecked")
  public static Tuple interpretStatement(String input, DependencyManager depman)
      throws FileNotFoundException {
    Pair<String, String> parserAndProgram =
        BaseParser.parseProgramToParserAndBody(input);
    String parserName = parserAndProgram.getFirst();
    String programBody = parserAndProgram.getSecond();

    Tuple parser =
        parserName.equals("__BUILTIN__") ? null : interpretStatement(depman
            .getResource(parserName), depman);

    Tuple mainProgExp =
        Lambda.car(Lambda.app(parser, Lambda.data(LSymbol.of(programBody))));
    Tuple depsListExp =
        Lambda.cdr(Lambda.app(parser, Lambda.data(LSymbol.of(programBody))));

    List<Object> depsList =
        (List<Object>) Util.convertPrimitiveToJava(Lambda.reduce(depsListExp));
    List<Tuple> depValues = new ArrayList();

    for (Object depObj : depsList) {
      String depName = (String) depObj;
      depValues.add(interpretStatement(depman.getResource(depName), depman));
    }

    return Lambda.app(mainProgExp, Util.convertJavaToPrimitive(depValues));
  }
}