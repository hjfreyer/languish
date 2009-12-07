package languish.util;

import java.util.Arrays;
import java.util.List;

import languish.base.Primitive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class PrimitiveTree {
  private final Object object;

  public static PrimitiveTree of(Primitive prim) {
    return new PrimitiveTree(prim);
  }

  public static PrimitiveTree of(List<PrimitiveTree> list) {
    return new PrimitiveTree(list);
  }

  public static PrimitiveTree of(PrimitiveTree... list) {
    return of(Arrays.asList(list));
  }

  @SuppressWarnings("unchecked")
  public static PrimitiveTree copyOf(Object obj) {
    if (obj instanceof List<?>) {
      List<PrimitiveTree> result = Lists.newLinkedList();

      for (Object child : (List<Object>) obj) {
        result.add(PrimitiveTree.copyOf(child));
      }

      return PrimitiveTree.of(ImmutableList.copyOf(result));
    }

    return PrimitiveTree.of(new Primitive(obj));
  }

  private PrimitiveTree(Object object) {
    this.object = object;
  }

  public boolean isList() {
    return object instanceof List<?>;
  }

  public boolean isPrimitive() {
    return object instanceof Primitive;
  }

  @SuppressWarnings("unchecked")
  public List<PrimitiveTree> asList() {
    return (List<PrimitiveTree>) object;
  }

  public Primitive asPrimitive() {
    return (Primitive) object;
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
    PrimitiveTree other = (PrimitiveTree) obj;
    if (object == null) {
      if (other.object != null)
        return false;
    } else if (!object.equals(other.object))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return object.toString();
  }
}
