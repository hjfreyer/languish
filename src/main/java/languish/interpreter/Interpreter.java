package languish.interpreter;

import languish.lambda.Expression;
import languish.prim.data.LObject;
import languish.prim.data.LUnit;

public class Interpreter {

  private LObject env = LUnit.UNIT;
  private Parser parser = new BuiltinParser();

  public Interpreter() {}

  public LObject processStatement(String input) {
    Statement statement = parser.parseStatement(input, env);

    switch (statement.getType()) {
    case DISPLAY:
      break;
    case SET_ENV:
      env = statement.getObject();
      break;
    case SET_PARSER:
      Expression expression = (Expression) statement.getObject();
      parser = new ExpressionBasedParser(expression);
      break;
    }
    return statement.getObject();
  }
}
