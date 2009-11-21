package languish.interpreter;

import static languish.util.Lambda.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import languish.lambda.Term;
import languish.primitives.LSymbol;

import com.hjfreyer.util.Pair;

public class BaseParser {

  private static final Pattern MODULE_REGEX =
      Pattern.compile("^#lang[ ]+([^ ]+);;(.*)$", Pattern.DOTALL);

  public static Term parseFromString(String string) {
    Pair<String, String> parserAndProgram = getParserAndProgram(string);
    String parserName = parserAndProgram.getFirst();
    String programBody = parserAndProgram.getSecond();

    Term resultExpression;

    if (parserName.equals("__BUILTIN__")) {
      resultExpression = TermParser.TERM.parse(programBody);
    } else {
      Term depList = cons(primitive(LSymbol.of(parserName)), Term.NULL);
      Term programApplication =
          abs(app(ref(1), primitive(LSymbol.of(programBody))));

      resultExpression = cons(primitive(LSymbol.of("LOAD")), //
          cons(depList, cons(programApplication, Term.NULL)));
    }

    return resultExpression;
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

  private BaseParser() {
  }
}
