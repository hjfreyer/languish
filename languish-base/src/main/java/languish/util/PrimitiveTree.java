package languish.util;

import java.util.List;

import languish.base.Primitive;
import languish.base.Primitives;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class PrimitiveTree {

	@SuppressWarnings("unchecked")
	public static Tree<Primitive> from(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Input cannot be null.");
		}
		if (obj instanceof Tree<?>) {
			return (Tree<Primitive>) obj;
		}

		if (obj instanceof List<?>) {
			List<Tree<Primitive>> result = Lists.newLinkedList();

			for (Object child : (List<Object>) obj) {
				result.add(PrimitiveTree.from(child));
			}

			return Tree.inode(ImmutableList.copyOf(result));
		}

		if (obj instanceof Primitive) {
			return Tree.leaf((Primitive) obj);
		}

		return Tree.leaf(new Primitive(obj));
	}

	public static <T> Tree<Primitive> fromTree(Tree<T> from) {
		return Trees.transform(from, Primitives.<T> asPrimitve());
	}
}
