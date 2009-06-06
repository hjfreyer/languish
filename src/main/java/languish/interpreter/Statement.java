package languish.interpreter;

import languish.prim.data.LObject;

public class Statement {

  public static enum Type {
    SET_ENV,
    SET_PARSER,
    DISPLAY
    // LObject (env) -> LObject
    ;
    //
    // public static Type fromId(int intValue) {
    // switch (intValue) {
    // case 0:
    // return SET_ENV;
    // case 1:
    // return SET_PARSER;
    // case 2:
    // return SET_EVAL;
    // case 3:
    // return DISPLAY;
    // default:
    // throw new AssertionError();
    // }
    // }
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
