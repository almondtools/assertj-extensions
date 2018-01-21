package net.amygdalum.extensions.assertj.iterables;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Condition;

public final class ArrayConditions {
	
	private ArrayConditions() {
	}

	@SafeVarargs
	public static <E> Condition<E[]> containingExactly(Condition<E>... requirements) {
		return containingExactly(asList(requirements));
	}

	public static <E> Condition<E[]> containingExactly(Iterable<Condition<E>> requirements) {
		List<String> requiredString = StreamSupport.stream(requirements.spliterator(), false).map(Object::toString).collect(toList());
		return new Condition<E[]>(actual -> {
			Iterator<E> actualIterator = asList(actual).iterator();
			Iterator<Condition<E>> requirementsIterator = requirements.iterator();

			while (actualIterator.hasNext() && requirementsIterator.hasNext()) {
				Condition<E> requirement = requirementsIterator.next();
				E actualElement = actualIterator.next();

				if (!requirement.matches(actualElement)) {
					return false;
				}
			}
			return !actualIterator.hasNext()
				&& !requirementsIterator.hasNext();
		}, "contains exactly: %s", requiredString);
	}
}
