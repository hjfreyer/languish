package languish.prim.data;

import java.util.Arrays;


public final class LInteger extends LObject {
  private final int value;

  public LInteger(int value) {
    this.value = value;
  }

  //
  // public Type getType() {
  // return Type.INTEGER;
  // }

  public static LInteger of(int i) {
    return new LInteger(i);
  }

  @Override
  public String toString() {
    return "" + value;
  }

  public int intValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof LInteger)) {
      return false;
    }
    return value == ((LInteger) obj).value;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { getClass(), value });
  }

  public String repr() {
    return null;
  }
}
