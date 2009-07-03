package languish.interpreter;

import languish.prim.data.LObject;

public class Statement {

  public static enum Type {
    EVAL,
    SET_PARSER,
    SET_ENV,
  }

  private final Type type;
  private final LObject object;

  public Statement(Type type, LObject object) {
    this.type = type;
    this.object = object;
  }

  public Type getType() {
    return type;
  }

  public LObject getObject() {
    return object;
  }
}
