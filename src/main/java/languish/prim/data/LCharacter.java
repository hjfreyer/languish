package languish.prim.data;

public final class LCharacter extends DataWrapper {
  private final char value;

  public LCharacter(char value) {
    this.value = value;
  }

  public char charValue() {
    return value;
  }

  @Override
  public Object getJavaValue() {
    return value;
  }
}
