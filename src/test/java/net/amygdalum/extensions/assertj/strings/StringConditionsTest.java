package net.amygdalum.extensions.assertj.strings;

import static net.amygdalum.extensions.assertj.conventions.UtilityClass.utilityClass;
import static net.amygdalum.extensions.assertj.strings.StringConditions.containing;
import static net.amygdalum.extensions.assertj.strings.StringConditions.containingWildcardPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.Not.not;

import org.junit.jupiter.api.Test;

public class StringConditionsTest {
	
	@Test
	public void testStringConditions() throws Exception {
		assertThat(StringConditions.class).satisfies(utilityClass().conventions());
	}

	@Test
	public void testContaining() throws Exception {
		assertThat("a sentence with some words").is(containing("a"));
		assertThat("a sentence with some words").is(containing("sentence"));
		assertThat("a sentence with some words").is(containing("with"));
		assertThat("a sentence with some words").is(containing("some"));
		assertThat("a sentence with some words").is(containing("words"));
		assertThat("a sentence with some words").is(containing("entence"));
		assertThat("a sentence with some words").is(containing(" "));
		assertThat("a sentence with some words").is(containing(""));

		assertThat("a sentence with some words").is(not(containing("phrase")));
		assertThat("a sentence with some words").is(not(containing("x")));
	}

	@Test
	public void testContainingWildcardPattern() throws Exception {
		assertThat("a sentence with some words").is(containingWildcardPattern("a*with"));
		assertThat("a sentence with some words").is(containingWildcardPattern("a?sentence*with"));
		assertThat("a sentence with some words").is(containingWildcardPattern("sentence?with"));
		assertThat("a sentence with some words").is(containingWildcardPattern(" * "));

		assertThat("a sentence with some words").is(not(containingWildcardPattern("phrase")));
		assertThat("a sentence with some words").is(not(containingWildcardPattern(" *x* ")));
	}

}
