package net.amygdalum.extensions.assertj.iterables;

import org.assertj.core.api.AssertFactory;

public class IteratedObjectAssertFactory<T> implements AssertFactory<T, IteratedObjectAssert<T>> {

	@Override
	public IteratedObjectAssert<T> createAssert(T t) {
		return new IteratedObjectAssert<>(t);
	}

}
