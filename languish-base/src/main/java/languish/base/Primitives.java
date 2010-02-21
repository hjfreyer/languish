package languish.base;

import com.google.common.base.Function;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Primitives {

	public static <T> Function<T, Primitive> asPrimitve() {
		return new Function<T, Primitive>() {
			public Primitive apply(T from) {
				return new Primitive(from);
			}
		};
	}

	public static Function<Primitive, String> asString() {
		return new Function<Primitive, String>() {
			public String apply(Primitive from) {
				return from.asString();
			}
		};
	}

	public static <T> Tree<Primitive> treeToPrimitiveTree(Tree<T> from) {
		return Trees.transform(from, Primitives.<T> asPrimitve());
	}

	private Primitives() {
	}
}
