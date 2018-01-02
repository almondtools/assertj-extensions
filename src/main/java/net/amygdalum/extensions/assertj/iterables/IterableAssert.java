package net.amygdalum.extensions.assertj.iterables;

import java.util.Iterator;

public class IterableAssert<ELEMENT> extends org.assertj.core.api.IterableAssert<ELEMENT> {
	
	public IterableAssert(Iterable<? extends ELEMENT> actual) {
		super(actual);
	}

	public IterableAssert(Iterator<? extends ELEMENT> actual) {
		super(actual);
	}

	public IteratingAssert<ELEMENT> iterate() {
	    return new IteratingAssert<ELEMENT>(actual);
	}

}
