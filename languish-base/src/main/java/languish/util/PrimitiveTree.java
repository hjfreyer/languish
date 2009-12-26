package languish.util;

import java.util.Arrays;
import java.util.List;

import languish.base.Primitive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;

// TODO replace with Tree<Primitive>
public class PrimitiveTree {
  private final Object object;

  public static Tree<Primitive> of(Primitive prim) {
    return Tree.leaf(prim);
  }

  public static Tree<Primitive> of(List<Tree<Primitive>> list) {
    return Tree.inode(list);
  }

  public static Tree<Primitive> of(Tree<Primitive>... list) {
    return of(Arrays.asList(list));
  }

  @SuppressWarnings("unchecked")
  public static Tree<Primitive> copyOf(Object obj) {
    if (obj instanceof Tree<?>) {
      return (Tree<Primitive>) obj;
    } else if (obj instanceof List<?>) {
      List<Tree<Primitive>> result = Lists.newLinkedList();

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
