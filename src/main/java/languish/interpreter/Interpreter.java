package languish.interpreter;

import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.prim.data.LObject;

public class Interpreter {

  private LObject env = Tuple.of();
  private Parser parser = new BuiltinParser();

  public Interpreter() {}

  public LObject processStatement(String input) {
    Statement statement = parser.parseStatement(input, env);
    LObject expression = statement.getObject();

    switch (statement.getType()) {
    case EVAL:
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
