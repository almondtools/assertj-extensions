package net.amygdalum.extensions.assertj.iterables;

import org.assertj.core.api.AbstractObjectAssert;

public class IteratedObjectAssert<ACTUAL> extends AbstractObjectAssert<IteratedObjectAssert<ACTUAL>, ACTUAL> {

	private IteratingAssert<ACTUAL> parent;

	public IteratedObjectAssert(ACTUAL actual) {
	    super(actual, IteratedObjectAssert.class);
	}

	public void setParent(IteratingAssert<ACTUAL> parent) {
		this.parent = parent;
	}

	public IteratedObjectAssert<ACTUAL> next() {
		return parent.next();
	}

}
