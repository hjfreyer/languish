package languish.base;

import com.google.common.base.Function;

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

	private Primitives() {
	}

}
