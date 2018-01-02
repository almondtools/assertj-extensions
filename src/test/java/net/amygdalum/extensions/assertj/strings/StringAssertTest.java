package net.amygdalum.extensions.assertj.strings;

import static net.amygdalum.extensions.assertj.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

public class StringAssertTest {

	@Test
	public void testContainsWildcardPattern() throws Exception {
		assertThatCode(() -> assertThat("some text to be matched").containsWildcardPattern("text*match?d")).doesNotThrowAnyException();
		assertThatCode(() -> assertThat("some text to be matched").doesNotContainWildcardPattern("text*match?d"))
			.isInstanceOf(AssertionError.class)
			.hasMessageContaining("not to contain pattern");
	}

	@Test
	public void testContainsWildcardPatternWithNullValues() throws Exception {
		assertThatCode(() -> assertThat("some text to be matched").containsWildcardPattern(null))
			.isInstanceOf(NullPointerException.class);
		assertThatCode(() -> assertThat((String) null).doesNotContainWildcardPattern("text*match?d"))
			.isInstanceOf(AssertionError.class)
			.hasMessageContaining("actual not to be null");

	}

	@Test
	public void testDoesNotContainWildcardPattern() throws Exception {
		assertThatCode(() -> assertThat("some text to be matched").containsWildcardPattern("other*text"))
			.isInstanceOf(AssertionError.class)
			.hasMessageContaining("to contain pattern");
		assertThatCode(() -> assertThat("some text to be matched").doesNotContainWildcardPattern("other*text")).doesNotThrowAnyException();
	}

}
