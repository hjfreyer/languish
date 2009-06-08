package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.Wrapper;

public final class LCharacter extends LObject {
  private final char value;

  public LCharacter(char value) {
    this.value = value;
  }

  //
  // public Type getType() {
  // return Type.STRING;
  // }

  @Override
  public String toString() {
    return "(!'" + value + "'!)";
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LCharacter
        && ((LCharacter) obj).value == value;
  }

  @Override
  public Expression getCanonicalForm() {
    return Wrapper.of(this);
  }

  @Override
  public int hashCode() {
    return value;
  }

  public char charValue() {
    return value;
  }

}
