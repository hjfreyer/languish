package languish.interpreter;

import static languish.lambda.Lambda.*;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.prim.data.LInteger;
import languish.prim.data.LSymbol;

public class ExpressionBasedParser extends Parser {

  private final LObject expression;

  public ExpressionBasedParser(LObject expression) {
    this.expression = expression;
  }

  @Override
  public Statement parseStatement(String statement, LObject environment) {

    Tuple parseCall =
        app(app(expression, data(LSymbol.of(statement))), environment);

    Tuple type = get(1, parseCall);
    Tuple exp = get(2, parseCall);

    int statementType = ((LInteger) Lambda.reduce(type)).intValue();

    return new Statement(Statement.Type.values()[statementType], exp);
  }
}
