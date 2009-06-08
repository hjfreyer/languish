package languish.interpreter;

import languish.prim.data.LExpressionWrapper;
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
      LExpressionWrapper expression =
          (LExpressionWrapper) statement.getObject();
      parser = new ExpressionBasedParser(expression.getExpression());
      break;
    }
    return statement.getObject();
  }
}
