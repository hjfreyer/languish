package languish.primitives;

public final class LSymbol extends AbstractPrimitive {
  private final String value;

  private LSymbol(String value) {
    this.value = value;
  }

  public static LSymbol of(String string) {
    return new LSymbol(string);
  }

  public String stringValue() {
    return value;
  }

  @Override
  public Object getJavaObject() {
    return value;
  }
}
