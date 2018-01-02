package net.amygdalum.extensions.assertj.iterables;

import static java.util.Arrays.asList;
import static net.amygdalum.extensions.assertj.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ListAssertTest {

	@Test
	public void testIterate() throws Exception {
		assertThatCode(() -> assertThat(asList("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A"))
			.next().satisfies(item -> assertThat(item).isEqualTo("B"))).doesNotThrowAnyException();
	}

	@Test
	public void testIterateWithNonMatchingList() throws Exception {
		assertThatCode(() -> assertThat(asList("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("B"))
			.next().satisfies(item -> assertThat(item).isEqualTo("C")))
				.isInstanceOf(AssertionError.class)
				.hasMessage("expected:<\"[B]\"> but was:<\"[A]\">");
	}

	@Test
	public void testIterateMatchingMoreThanActual() throws Exception {
		assertThatCode(() -> assertThat(asList("A")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A"))
			.next().satisfies(item -> assertThat(item).isEqualTo("B")))
				.isInstanceOf(NoSuchElementException.class)
				.hasMessage("trying to match next element but no element left");
	}

	@Test
	public void testIterateMatchingLessThanActual() throws Exception {
		assertThatCode(() -> assertThat(asList("A", "B")).iterate()
			.next().satisfies(item -> assertThat(item).isEqualTo("A")))
				.doesNotThrowAnyException();
	}

}
