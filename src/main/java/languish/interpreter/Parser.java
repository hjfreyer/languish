package languish.interpreter;

import languish.lambda.LObject;

public abstract class Parser {
  public abstract Statement parseStatement(String statement, LObject env);
}
