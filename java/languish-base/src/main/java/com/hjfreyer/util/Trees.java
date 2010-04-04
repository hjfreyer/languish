package com.hjfreyer.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class Trees {
  public static <A, B> Tree<B> transform(Tree<A> from, Function<A, B> function) {
    if (from.isLeaf()) {
      return Tree.leaf(function.apply(from.asLeaf()));
    }

    final Function<A, B> finalFunc = function;

    return Tree.inode(Lists.transform(
        from.asList(),
        new Function<Tree<A>, Tree<B>>() {
          public Tree<B> apply(Tree<A> from) {
            return transform(from, finalFunc);
          }
        }));
  }
}
