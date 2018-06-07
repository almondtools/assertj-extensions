package net.amygdalum.extensions.assertj.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AStringTest {

	@Test
	void testMatching() throws Exception {
		assertThat(AString.matching("my string").description().value()).isEqualTo("matches \"my string\"");
	}

}
