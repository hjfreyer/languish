package languish.parsing;

import languish.base.Tuple;
import languish.interpreter.Module;

public abstract class Parser {
  public abstract Module parseModule(String statement, Tuple env);
}
