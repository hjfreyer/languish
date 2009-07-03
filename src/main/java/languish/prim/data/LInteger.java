package languish.prim.data;

import languish.lambda.ImmutableLObject;

public final class LInteger extends ImmutableLObject {
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

  public String getLiteral() {
    return "" + value;
  }

  @Override
  public String toString() {
    return "LInteger.of(" + value + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LInteger other = (LInteger) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }
}
