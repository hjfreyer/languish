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

  public static Tuple interpretStatement(String input, DependencyManager depman)
      throws FileNotFoundException {
    Pair<String, String> parserAndProgram = getParserAndProgram(input);
    String parserName = parserAndProgram.getFirst();
    String programBody = parserAndProgram.getSecond();

    Tuple resultExpression;

    if (parserName.equals("__BUILTIN__")) {
      resultExpression = BuiltinParser.SINGLE_TUPLE.parse(programBody);
    } else {
      Tuple parser = depman.getResource(parserName);

      resultExpression =
          Lambda.app(parser, Lambda.data(LSymbol.of(programBody)));
    }
    return evaluateToValue(resultExpression, depman);
  }

  public static Tuple evaluateToValue(Tuple tuple, DependencyManager depman)
      throws FileNotFoundException {
    LSymbol returnType = (LSymbol) Lambda.reduceToDataValue(Lambda.car(tuple));

    if (returnType.stringValue().equals("VALUE")) {
      return Lambda.cdr(tuple);
    } else if (returnType.stringValue().equals("LOAD")) {
      Tuple load = Lambda.cdr(tuple);

      Tuple depList = Lambda.car(load);
      Tuple exp = Lambda.cdr(load);

      @SuppressWarnings("unchecked")
      List<String> depsList =
          (List<String>) Util.convertPrimitiveToJava(Lambda.reduce(depList));

      List<Tuple> depValues = new ArrayList<Tuple>();

      for (String depName : depsList) {
        depValues.add(depman.getResource(depName));
      }

      return evaluateToValue(Lambda.app(exp, Util
          .convertJavaToPrimitive(depValues)), depman);
    } else {
      throw new AssertionError();
    }
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