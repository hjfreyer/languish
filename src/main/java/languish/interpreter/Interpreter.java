package languish.interpreter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import languish.lambda.Term;
import languish.primitives.LSymbol;
import languish.util.Lambda;
import languish.util.Util;

import com.hjfreyer.util.Pair;

public class Interpreter {

  private static final Pattern MODULE_REGEX =
      Pattern.compile("^#lang[ ]+([^ ]+);;(.*)$", Pattern.DOTALL);

  private Interpreter() {
  }

  public static Term interpretStatement(String input, DependencyManager depman)
      throws FileNotFoundException {
    Pair<String, String> parserAndProgram = getParserAndProgram(input);
    String parserName = parserAndProgram.getFirst();
    String programBody = parserAndProgram.getSecond();

    Term resultExpression;

    if (parserName.equals("__BUILTIN__")) {
      resultExpression = TermParser.TERM.parse(programBody);
    } else {
      Term parser = depman.getResource(parserName);

      resultExpression =
          Lambda.app(parser, Lambda.primitive(LSymbol.of(programBody)));
    }
    return evaluateToValue(resultExpression, depman);
  }

  public static Term evaluateToValue(Term tuple, DependencyManager depman)
      throws FileNotFoundException {
    LSymbol returnType = (LSymbol) Lambda.reduceToDataValue(Lambda.car(tuple));

    if (returnType.stringValue().equals("VALUE")) {
      return Lambda.cdr(tuple);
    } else if (returnType.stringValue().equals("LOAD")) {
      Term load = Lambda.cdr(tuple);

      Term depList = Lambda.car(load);
      Term exp = Lambda.cdr(load);

      @SuppressWarnings("unchecked")
      List<String> depsList =
          (List<String>) Util.convertTermToJavaObject(Lambda.reduce(depList));

      List<Term> depValues = new ArrayList<Term>();

      for (String depName : depsList) {

        depValues.add(depman.getResource(depName));
      }

      return evaluateToValue(Lambda.app(exp, Util
          .convertJavaObjectToTerm(depValues)), depman);
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