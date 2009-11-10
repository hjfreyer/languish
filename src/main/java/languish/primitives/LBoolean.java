package languish.primitives;


public final class LBoolean extends AbstractPrimitive {

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
  public Object getJavaObject() {
    return value;
  }

  public static LBoolean of(boolean b) {
    return b ? TRUE : FALSE;
  }

}
