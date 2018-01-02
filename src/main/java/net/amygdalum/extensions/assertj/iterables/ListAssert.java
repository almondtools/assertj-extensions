package net.amygdalum.extensions.assertj.iterables;

import java.util.List;

public class ListAssert<ELEMENT> extends org.assertj.core.api.ListAssert<ELEMENT> {

	public ListAssert(List<? extends ELEMENT> actual) {
		super(actual);
	}

	public IteratingAssert<ELEMENT> iterate() {
		return new IteratingAssert<ELEMENT>(actual);
	}

}
