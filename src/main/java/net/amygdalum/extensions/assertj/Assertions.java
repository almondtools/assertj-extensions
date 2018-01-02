package net.amygdalum.extensions.assertj;

import java.util.Iterator;
import java.util.List;

import net.amygdalum.extensions.assertj.iterables.IterableAssert;
import net.amygdalum.extensions.assertj.iterables.ListAssert;
import net.amygdalum.extensions.assertj.strings.StringAssert;

public class Assertions extends org.assertj.core.api.Assertions {
	
	public Assertions() {
	}

	public static StringAssert assertThat(String actual) {
		return new StringAssert(actual);
	}

	public static <ELEMENT> IterableAssert<ELEMENT> assertThat(Iterator<? extends ELEMENT> actual) {
		return new IterableAssert<>(actual);
	}

	public static <ELEMENT> IterableAssert<ELEMENT> assertThat(Iterable<? extends ELEMENT> actual) {
		return new IterableAssert<>(actual);
	}

	public static <ELEMENT> ListAssert<ELEMENT> assertThat(List<? extends ELEMENT> actual) {
		return new ListAssert<>(actual);
	}

}
