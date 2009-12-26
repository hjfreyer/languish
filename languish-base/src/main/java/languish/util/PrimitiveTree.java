package languish.util;

import java.util.List;

import languish.base.Primitive;

import com.hjfreyer.util.Tree;

// TODO replace with Tree<Primitive>
public class PrimitiveTree {
  private final Object object;

  public static Tree<Primitive> of(Primitive prim) {
    return null;
  }

  public static Tree<Primitive> of(List<?> list) {
    return null;
  }

  public static Tree<Primitive> of(PrimitiveTree... list) {
    return null;
  }

  @SuppressWarnings("unchecked")
  public static Tree<Primitive> copyOf(Object obj) {
    return null;
  }

  private PrimitiveTree(Object object) {
    this.object = object;
  }

  public boolean isList() {
    return object instanceof List<?>;
  }

  public boolean isLeaf() {
    return object instanceof Primitive;
  }

  @SuppressWarnings("unchecked")
  public List<Tree<Primitive>> asList() {
    return (List<Tree<Primitive>>) object;
  }

  public Primitive asLeaf() {
    return (Primitive) object;
  }
}
