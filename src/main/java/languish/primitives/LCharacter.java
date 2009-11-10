package languish.primitives;


public final class LCharacter extends AbstractPrimitive {
  private final char value;

  private LCharacter(char value) {
    this.value = value;
  }

  public static LCharacter of(char c) {
    return new LCharacter(c);
  }

  public char charValue() {
    return value;
  }

  @Override
  public Object getJavaObject() {
    return value;
  }
}
