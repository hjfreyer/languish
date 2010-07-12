package com.hjfreyer.util;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class Pair<A, B> {

	public static <A, B> Function<Pair<A, B>, A> first() {
		return new Function<Pair<A, B>, A>() {
			@Override
			public A apply(Pair<A, B> arg) {
				return arg.getFirst();
			}
		};
	}

	public static <A, B> Function<Pair<A, B>, B> second() {
		return new Function<Pair<A, B>, B>() {
			@Override
			public B apply(Pair<A, B> arg) {
				return arg.getSecond();
			}
		};
	}

	private final A first;
	private final B second;

	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public static <A, B> Pair<A, B> of(A first, B second) {
		return new Pair<A, B>(first, second);
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "Pair.of(" + first + ", " + second + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		@SuppressWarnings("unchecked")
		Pair<A, B> other = (Pair<A, B>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	public static <T> Function<Pair<T, T>, List<T>> asList() {
		return new Function<Pair<T, T>, List<T>>() {
			@Override
			public List<T> apply(Pair<T, T> from) {
				return ImmutableList.of(from.getFirst(), from.getSecond());
			}
		};
	}

}
