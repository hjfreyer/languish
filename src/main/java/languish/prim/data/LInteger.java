package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.Wrapper;

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
    return "(!" + value + "!)";
  }

  public int intValue() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + value;
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
    LInteger other = (LInteger) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

  public String repr() {
    return null;
  }
}

// (APP (APP (~GET_ELEMENT~) (APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!)))
// (!0!))>
// (APP (APP (~GET_ELEMENT~) (APP (APP (APP (~WRAP~) (!2!)) (!4!)) (!5!)))
// (!0!))>
