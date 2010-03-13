package languish.base;

import com.google.common.base.Function;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class Primitives {

	public static Primitive NULL = new Primitive(0);

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

	public static Primitive parse(String string) {
		if (string.startsWith("s")) {
			return new Primitive(string.substring(1).replaceAll("\\\\(.)", "\\1"));
		} else if (string.startsWith("i")) {
			return new Primitive(Integer.parseInt(string.substring(1)));
		}
		throw new IllegalArgumentException("String cannot be parsed to primitive: "
				+ string);
	}
}
