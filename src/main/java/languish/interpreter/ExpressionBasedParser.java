package languish.interpreter;

import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.Reducer;
import languish.lambda.Wrapper;
import languish.prim.data.LComposite;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;
import languish.prim.data.LSymbol;

public class ExpressionBasedParser extends Parser {

  private final Expression expression;

  public ExpressionBasedParser(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Statement parseStatement(String statement, LObject environment) {

    Application parseCall =
        Application.of(Application.of(expression, Wrapper.of(LSymbol
            .of(statement))), Wrapper.of(environment));

    LComposite result = (LComposite) Reducer.reduce(parseCall);

    int statementType = ((LInteger) result.get(0)).intValue();
    LObject statementObject = result.get(1);

    return new Statement(Statement.Type.values()[statementType],
        statementObject);
  }

}
