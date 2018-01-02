package net.amygdalum.extensions.assertj.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.assertj.core.api.FactoryBasedNavigableIterableAssert;

public class IteratingAssert<ELEMENT> extends FactoryBasedNavigableIterableAssert<IteratingAssert<ELEMENT>, Iterable<? extends ELEMENT>, ELEMENT, IteratedObjectAssert<ELEMENT>> {

	private Iterator<? extends ELEMENT> iterator;

	public IteratingAssert(Iterable<? extends ELEMENT> actual) {
	    super(actual, IteratingAssert.class, new IteratedObjectAssertFactory<ELEMENT>());
		this.iterator = this.actual.iterator();
	}

	@Override
	public IteratedObjectAssert<ELEMENT> toAssert(ELEMENT value, String description) {
		IteratedObjectAssert<ELEMENT> itemAssert = super.toAssert(value, description);
		itemAssert.setParent(this);
		return itemAssert;
	}
	
	public IteratedObjectAssert<ELEMENT> next() {
		if (!iterator.hasNext()) {
			throw new NoSuchElementException("trying to match next element but no element left");
		}
		IteratedObjectAssert<ELEMENT> iterated = toAssert(iterator.next(), navigationDescription("check next element"));
		return iterated;
	}

}
