package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.Wrapper;

public final class LSymbol extends LObject {
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
  public int hashCode() {
    final int prime = 31;
    int result = 0;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public Expression getCanonicalForm() {
    return Wrapper.of(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LSymbol other = (LSymbol) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

}
