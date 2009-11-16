package languish.util;

import java.util.List;

public class JavaWrapper {
  private final Object object;

  public static JavaWrapper of(Object obj) {
    return new JavaWrapper(obj);
  }

  @SuppressWarnings("unchecked")
  public List<JavaWrapper> asList() {
    return (List<JavaWrapper>) object;
  }

  public boolean isList() {
    return object instanceof List<?>;
  }

  public boolean asBoolean() {
    return (Boolean) object;
  }

  public boolean isBoolean() {
    return object instanceof Boolean;
  }

  public char asCharacter() {
    return (Character) object;
  }

  public boolean isCharacter() {
    return object instanceof Character;
  }

  public int asInteger() {
    return (Integer) object;
  }

  public boolean isInteger() {
    return object instanceof Integer;
  }

  public String asString() {
    return (String) object;
  }

  public boolean isString() {
    return object instanceof String;
  }

  private JavaWrapper(Object object) {
    this.object = object;
  }

  @Override
  public String toString() {
    return "JavaWrapper [object=" + object + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((object == null) ? 0 : object.hashCode());
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
    JavaWrapper other = (JavaWrapper) obj;
    if (object == null) {
      if (other.object != null)
        return false;
    } else if (!object.equals(other.object))
      return false;
    return true;
  }
}
