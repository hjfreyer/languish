package languish.lambda;

import languish.prim.data.LObject;
import languish.prim.data.LUnit;

public class Wrapper extends Expression {

  public static final Wrapper NULL = Wrapper.of(LUnit.UNIT);

  private final LObject contents;

  private Wrapper(LObject contents) {
    this.contents = contents;
  }

  public LObject getContents() {
    return contents;
  }

  public static Wrapper of(LObject contents) {
    return new Wrapper(contents);
  }

  // public static Literal of(Abstraction a) {
  // return of(Function.of(a));
  // // }
  //
  // public static Wrapper of(int i) {
  // return of(new LInteger(i));
  // }

  @Override
  public Type getType() {
    return Type.WRAPPER;
  }

  @Override
  public String toString() {
    return "(!" + contents + "!)";
  }

  //
  // @Override
  // public String repr() {
  // return "(~MK_BLOB " + contents.repr() + "~)";
  // }

  @Override
  public Expression reduceOnce() {
    return this;
  }

  @Override
  public Expression replaceAllReferencesToParam(int id, Expression with) {
    return this;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contents == null) ? 0 : contents.hashCode());
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
    Wrapper other = (Wrapper) obj;
    if (contents == null) {
      if (other.contents != null) {
        return false;
      }
    } else if (!contents.equals(other.contents)) {
      return false;
    }
    return true;
  }

  //
  // public static Wrapper of(boolean b) {
  // return of(LBoolean.of(b));
  // }
}