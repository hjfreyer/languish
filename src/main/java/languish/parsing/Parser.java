package languish.parsing;

import languish.base.LObject;
import languish.interpreter.Statement;

public abstract class Parser {
  public abstract Statement parseStatement(String statement, LObject env);
}
