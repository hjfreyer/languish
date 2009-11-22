package languish.lambda;

public class Primitive {
  private final Object wrapped;

  public Primitive(Object wrapped) {
    if (wrapped instanceof Boolean || wrapped instanceof Character
        || wrapped instanceof Double || wrapped instanceof Integer
        || wrapped instanceof String || wrapped instanceof NativeFunction) {
      this.wrapped = wrapped;
    } else {
      throw new IllegalArgumentException("Object not a primitive: " + wrapped);
    }
  }

  public boolean asBoolean() {
    return (Boolean) wrapped;
  }

  public boolean isBoolean() {
    return wrapped instanceof Boolean;
  }

  public char asCharacter() {
    return (Character) wrapped;
  }

  public boolean isCharacter() {
    return wrapped instanceof Character;
  }

  public int asInteger() {
    return (Integer) wrapped;
  }

  public boolean isInteger() {
    return wrapped instanceof Integer;
  }

  public String asString() {
    return (String) wrapped;
  }

  public boolean isString() {
    return wrapped instanceof String;
  }

  public Object getJavaObject() {
    return wrapped;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((wrapped == null) ? 0 : wrapped.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Primitive other = (Primitive) obj;
    if (wrapped == null) {
      if (other.wrapped != null)
        return false;
    } else if (!wrapped.equals(other.wrapped))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Primitive [wrapped=" + wrapped + "]";
  }
}
