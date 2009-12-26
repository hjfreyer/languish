package com.hjfreyer.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Join;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Tree<T> implements Reprable {

  public static <T> Function<Tree<T>, T> asLeafFunction() {
    return new Function<Tree<T>, T>() {
      @Override
      public T apply(Tree<T> from) {
        return from.asLeaf();
      }
    };
  }

  private final Object node;

  public static <T> Tree<T> leaf(T prim) {
    return new Tree<T>(prim);
  }

  public static <T> Tree<T> empty() {
    return new Tree<T>(ImmutableList.<T> of());
  }

  public static <T> Tree<T> inode(List<Tree<T>> list) {
    return new Tree<T>(list);
  }

  public static <T> Tree<T> inode(Tree<T>... list) {
    return inode(Arrays.asList(list));
  }

  @SuppressWarnings("unchecked")
  public static <T> Tree<T> copyOf(Object obj) {
    if (obj instanceof List<?>) {
      List<Tree<T>> result = Lists.newLinkedList();

      for (Object child : (List<Object>) obj) {
        result.add(Tree.<T> copyOf(child));
      }

      return Tree.inode(ImmutableList.copyOf(result));
    }

    return Tree.leaf((T) obj);
  }

  private Tree(Object node) {
    this.node = node;
  }

  public boolean isLeaf() {
    return !(node instanceof List<?>);
  }

  public boolean isList() {
    return (node instanceof List<?>);
  }

  @SuppressWarnings("unchecked")
  public List<Tree<T>> asList() {
    return (List<Tree<T>>) node;
  }

  @SuppressWarnings("unchecked")
  public T asLeaf() {
    return (T) node;
  }

  public Object getNode() {
    return node;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((node == null) ? 0 : node.hashCode());
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
    Tree<?> other = (Tree<?>) obj;
    if (node == null) {
      if (other.node != null)
        return false;
    } else if (!node.equals(other.node))
      return false;
    return true;
  }

  @Override
  public String toString() {
    if (isLeaf()) {
      return node.toString();
    }

    List<String> children = Lists.transform(asList(), Functions.TO_STRING);
    return "[" + Join.join(", ", children) + "]";
  }

  @Override
  public String repr() {
    if (isLeaf()) {
      return "Tree.leaf(" + Repr.repr(node) + ")";
    }

    List<String> reprs = Lists.transform(asList(), Repr.TO_REPR);
    return "Tree.inode(" + Join.join(", ", reprs) + ")";
  }
}
