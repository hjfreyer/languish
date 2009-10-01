package languish.interpreter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import languish.base.Lambda;
import languish.base.Tuple;
import languish.base.Util;
import languish.primitives.LSymbol;

import com.hjfreyer.util.Pair;

public class Interpreter {

  private static final Pattern MODULE_REGEX =
      Pattern.compile("^#lang[ ]+([^ ]+);;(.*)$", Pattern.DOTALL);

  private Interpreter() {
  }

  @SuppressWarnings("unchecked")
  public static Tuple interpretStatement(String input, DependencyManager depman)
      throws FileNotFoundException {
    Pair<String, String> parserAndProgram = getParserAndProgram(input);
    String parserName = parserAndProgram.getFirst();
    String programBody = parserAndProgram.getSecond();

    if (parserName.equals("__BUILTIN__")) {
      List<Tuple> statements = BuiltinParser.PROGRAM.parse(programBody);

      Tuple result = Lambda.data(Tuple.of());

      for (Tuple statement : statements) {
        result = Lambda.app(statement, result);
      }

      return result;
    }

    Tuple parser = depman.getResource(parserName);

    Tuple mainProgExp =
        Lambda.car(Lambda.app(parser, Lambda.data(LSymbol.of(programBody))));
    Tuple depsListExp =
        Lambda.cdr(Lambda.app(parser, Lambda.data(LSymbol.of(programBody))));

    List<Object> depsList =
        (List<Object>) Util.convertPrimitiveToJava(Lambda.reduce(depsListExp));
    List<Tuple> depValues = new ArrayList();

    for (Object depObj : depsList) {
      String depName = (String) depObj;
      depValues.add(depman.getResource(depName));
    }

    return Lambda.app(mainProgExp, Util.convertJavaToPrimitive(depValues));
  }

  public static Pair<String, String> getParserAndProgram(String input) {
    Matcher match = MODULE_REGEX.matcher(input.trim());

    if (!match.matches()) {
      return Pair.of("__BUILTIN__", input);
    }

    String parser = match.group(1);
    String program = match.group(2);

    return Pair.of(parser, program);
  }
}