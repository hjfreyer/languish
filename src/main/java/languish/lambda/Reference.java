package languish.lambda;

public class Reference extends Expression {

  private final int index;

  private Reference(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  @Override
  public Type getType() {
    return Type.REFERENCE;
  }

  public static Reference to(int index) {
    return new Reference(index);
  }

  @Override
  public String toString() {
    return "Reference.to(" + index + ")";
  }

  //
  // @Override
  // public Expression reduceOnce() {
  // throw new UnsupportedOperationException();
  // }
  //
  // @Override
  // public Expression replaceAllReferencesToParam(int id, Expression with) {
  // return (index == id) ? with : this;
  // }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + index;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Reference other = (Reference) obj;
    if (index != other.index) {
      return false;
    }
    return true;
  }

}