package languish.prim.data;


public final class LCharacter extends LObject {
  private final char value;

  public LCharacter(char value) {
    this.value = value;
  }

  public char charValue() {
    return value;
  }

  public String getLiteral() {
    return "'" + value + "'";
  }

  @Override
  public String toString() {
    return "LCharacter.of('" + value + "')";
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
    LCharacter other = (LCharacter) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

}
