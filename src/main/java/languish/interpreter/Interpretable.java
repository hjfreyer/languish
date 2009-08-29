package languish.interpreter;

import languish.parsing.Parser;

public final class Interpretable {
  private final Parser parser;
  private final String statement;

  private Interpretable(Parser parser, String statement) {
    this.parser = parser;
    this.statement = statement;
  }

  public Parser getParser() {
    return parser;
  }

  public String getStatement() {
    return statement;
  }
}
