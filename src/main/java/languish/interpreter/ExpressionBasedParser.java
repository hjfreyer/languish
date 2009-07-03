package languish.interpreter;

import languish.lambda.Application;
import languish.lambda.Data;
import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;
import languish.prim.data.LSymbol;

public class ExpressionBasedParser extends Parser {

  private final LObject expression;

  public ExpressionBasedParser(LObject expression) {
    this.expression = expression;
  }

  @Override
  public Statement parseStatement(String statement, LObject environment) {

    LObject parseCall =
        Application.of(Application.of(expression, Data
            .of(LSymbol.of(statement))), environment);

    Tuple type = Tuple.of(Lambda.GET, Tuple.of(LInteger.of(0), parseCall));
    Tuple exp = Tuple.of(Lambda.GET, Tuple.of(LInteger.of(1), parseCall));

    int statementType = ((LInteger) Lambda.reduce(type)).intValue();

    return new Statement(Statement.Type.values()[statementType], exp);
  }
}
