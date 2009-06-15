package languish.prim.data;

import java.util.Arrays;

public final class LBoolean extends LObject {

  private final boolean value;

  public static LBoolean TRUE = new LBoolean(true);
  public static LBoolean FALSE = new LBoolean(false);

  private LBoolean(boolean value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LBoolean
        && ((LBoolean) obj).value == value;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { getClass(), value });
  }

  public boolean booleanValue() {
    return value;
  }

  @Override
  public String toString() {
    return value ? "TRUE" : "FALSE";
  }

  public static LObject of(boolean b) {
    return new LBoolean(b);
  }

}
