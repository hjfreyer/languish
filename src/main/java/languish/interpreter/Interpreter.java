package languish.interpreter;

import languish.prim.data.LObject;
import languish.prim.data.LUnit;
import languish.prim.data.exp.LExpression;

public class Interpreter {

  private LObject env = LUnit.instance();
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
      LExpression expression = (LExpression) statement.getObject();
      parser = new ExpressionBasedParser(expression.toExpression());
      break;
    }
    return statement.getObject();
  }
}
