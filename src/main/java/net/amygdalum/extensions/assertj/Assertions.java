package net.amygdalum.extensions.assertj;

import net.amygdalum.extensions.assertj.strings.StringAssert;

public class Assertions extends org.assertj.core.api.Assertions {

	public Assertions() {
	}

	public static StringAssert assertThat(String actual) {
		return new StringAssert(actual);
	}

}
