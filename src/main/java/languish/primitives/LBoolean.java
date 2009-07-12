package languish.primitives;

import languish.base.LObject;

public final class LBoolean extends DataWrapper {

  private final boolean value;

  public static LBoolean TRUE = new LBoolean(true);
  public static LBoolean FALSE = new LBoolean(false);

  private LBoolean(boolean value) {
    this.value = value;
  }

  public boolean booleanValue() {
    return value;
  }

  @Override
  public Object getJavaValue() {
    return value;
  }

  public static LObject of(boolean b) {
    return b ? TRUE : FALSE;
  }

}
