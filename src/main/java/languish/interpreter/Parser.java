package languish.interpreter;

import languish.prim.data.LObject;

public abstract class Parser {
  public abstract Statement parseStatement(String statement, LObject env);
}
