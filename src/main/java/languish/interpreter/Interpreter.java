package languish.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import languish.error.DependencyUnavailableError;
import languish.lambda.Term;
import languish.primitives.LSymbol;
import languish.util.JavaWrapper;
import languish.util.Lambda;

import com.hjfreyer.util.Pair;

public class Interpreter {

  private static final Pattern MODULE_REGEX =
      Pattern.compile("^#lang[ ]+([^ ]+);;(.*)$", Pattern.DOTALL);

  private Interpreter() {
  }

  public static Term interpretStatement(String input, DependencyManager depman)
      throws DependencyUnavailableError {
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
      throws DependencyUnavailableError {
    String returnType =
        Lambda.convertTermToJavaObject(Lambda.car(tuple)).asString();

    if (returnType.equals("VALUE")) {
      return Lambda.cdr(tuple);
    } else if (returnType.equals("LOAD")) {
      Term load = Lambda.cdr(tuple);

      Term depList = Lambda.car(load);
      Term exp = Lambda.cdr(load);

      @SuppressWarnings("unchecked")
      List<String> depsList =
          (List<String>) Lambda.convertTermToJavaObject(depList);

      List<Term> depValues = new ArrayList<Term>();

      for (String depName : depsList) {
        depValues.add(depman.getResource(depName));
      }

      return evaluateToValue(Lambda.app(exp, Lambda
          .convertJavaObjectToTerm(JavaWrapper.of(depValues))), depman);
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