package languish.parsing;

import static languish.base.Lambda.*;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.Statement;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

public class ExpressionBasedParser extends Parser {

  private final Tuple expression;

  public ExpressionBasedParser(Tuple expression) {
    this.expression = expression;
  }

  @Override
  public Statement parseStatement(String statement, Tuple environment) {

    Tuple parseCall =
        app(app(expression, data(LSymbol.of(statement))), environment);

    Tuple type = get(1, parseCall);
    Tuple exp = get(2, parseCall);

    int statementType = ((LInteger) Lambda.reduce(type)).intValue();

    return new Statement(Statement.Type.values()[statementType], exp);
  }
}