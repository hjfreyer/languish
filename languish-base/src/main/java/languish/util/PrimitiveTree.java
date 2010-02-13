package languish.util;

import java.util.List;

import languish.base.Primitive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;

public class PrimitiveTree {

  @SuppressWarnings("unchecked")
  public static Tree<Primitive> from(Object obj) {
    if (obj instanceof Tree<?>) {
      return (Tree<Primitive>) obj;
    } else if (obj instanceof List<?>) {
      List<Tree<Primitive>> result = Lists.newLinkedList();

      for (Object child : (List<Object>) obj) {
        result.add(PrimitiveTree.from(child));
      }

      return Tree.inode(ImmutableList.copyOf(result));
    } else if (obj instanceof Primitive) {
      return Tree.leaf((Primitive) obj);
    }

    return Tree.leaf(new Primitive(obj));
  }
}
