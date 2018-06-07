package net.amygdalum.extensions.assertj.iterables;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Condition;

public final class IterableConditions {

	private IterableConditions() {
	}
	
	@SafeVarargs
	public static <T extends Iterable<? extends E>, E> Condition<? super T> containingExactly(Condition<E>... requirements) {
		return containingExactly(asList(requirements));
	}

	public static <T extends Iterable<? extends E>, E> Condition<? super T> containingExactly(Iterable<Condition<E>> requirements) {
		List<String> requiredString = StreamSupport.stream(requirements.spliterator(), false).map(req -> req.description().value()).collect(toList());
		return new Condition<T>(actual -> {
			Iterator<? extends E> actualIterator = actual.iterator();
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
