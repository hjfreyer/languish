package languish.interpreter;

import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.parsing.BuiltinParser;
import languish.parsing.ExpressionBasedParser;
import languish.parsing.Parser;

public class Interpreter {

  private LObject env = Lambda.data(Tuple.of());
  private Parser parser = new BuiltinParser();

  public Interpreter() {}

  public LObject processStatement(String input) {
    Statement statement = parser.parseStatement(input, env);
    LObject expression = statement.getObject();

    switch (statement.getType()) {
    case REDUCE:
      return Lambda.reduce(expression);
    case SET_ENV:
      env = expression;
      return expression;
    case SET_PARSER:
      parser = new ExpressionBasedParser(expression);
      return expression;
    default:
      throw new AssertionError();
    }
  }
}
