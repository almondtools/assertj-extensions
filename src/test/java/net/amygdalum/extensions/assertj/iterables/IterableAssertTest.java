package net.amygdalum.extensions.assertj.iterables;

import static java.util.Arrays.asList;
import static net.amygdalum.extensions.assertj.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class IterableAssertTest {

	@Test
	public void testIterate() throws Exception {
		assertThatCode(() -> assertThat(asSet("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A"))
			.next().satisfies(item -> assertThat(item).isEqualTo("B"))).doesNotThrowAnyException();
		assertThatCode(() -> assertThat(asSet("A", "B").iterator()).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A"))
			.next().satisfies(item -> assertThat(item).isEqualTo("B"))).doesNotThrowAnyException();
	}

	@Test
	public void testIterateWithNonMatchingList() throws Exception {
		assertThatCode(() -> assertThat(asSet("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("B"))
			.next().satisfies(item -> assertThat(item).isEqualTo("C")))
				.isInstanceOf(AssertionError.class)
				.hasMessage("expected:<\"[B]\"> but was:<\"[A]\">");
	}

	@Test
	public void testIterateMatchingMoreThanActual() throws Exception {
		assertThatCode(() -> assertThat(asSet("A")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A"))
			.next().satisfies(item -> assertThat(item).isEqualTo("B")))
				.isInstanceOf(NoSuchElementException.class)
				.hasMessage("trying to match next element but no element left");
	}

	@Test
	public void testIterateMatchingLessThanActual() throws Exception {
		assertThatCode(() -> assertThat(asSet("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A")))
				.doesNotThrowAnyException();
	}

	@SafeVarargs
	private final <T> Set<T> asSet(T... elements) {
		return new HashSet<>(asList(elements));
	}
}
