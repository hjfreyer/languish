package languish.prim.data;


public final class LInteger extends DataWrapper {
  private final int value;

  public LInteger(int value) {
    this.value = value;
  }

  public static LInteger of(int i) {
    return new LInteger(i);
  }

  public int intValue() {
    return value;
  }

  @Override
  public Object getJavaValue() {
    return value;
  }
}
