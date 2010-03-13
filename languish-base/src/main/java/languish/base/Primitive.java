package languish.base;

public class Primitive {

	private final Object wrapped;

	public Primitive(Object wrapped) {
		if (wrapped instanceof Integer || wrapped instanceof String) {
			this.wrapped = wrapped;
		} else {
			throw new IllegalArgumentException("Object not a primitive: " + wrapped);
		}
	}

	public int asInteger() {
		return (Integer) wrapped;
	}

	public boolean isInteger() {
		return wrapped instanceof Integer;
	}

	public String asString() {
		return ((String) wrapped).replace("\"", "\\\"");
	}

	public boolean isString() {
		return wrapped instanceof String;
	}

	public Object getJavaObject() {
		return wrapped;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wrapped == null) ? 0 : wrapped.hashCode());
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
		Primitive other = (Primitive) obj;
		if (wrapped == null) {
			if (other.wrapped != null)
				return false;
		} else if (!wrapped.equals(other.wrapped))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (isInteger()) {
			return "i" + asInteger();
		}

		if (isString()) {
			return "s" + asString();
		}

		throw new AssertionError();
	}
}
