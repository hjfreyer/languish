package languish.parsing;

import languish.base.Tuple;
import languish.interpreter.Statement;

public abstract class Parser {
  public abstract Statement parseStatement(String statement, Tuple env);
}
