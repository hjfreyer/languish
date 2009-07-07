package languish.primitives;

public final class LSymbol extends DataWrapper {
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
  public Object getJavaValue() {
    return value;
  }
}
