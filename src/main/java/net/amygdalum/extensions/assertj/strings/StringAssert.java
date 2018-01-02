package net.amygdalum.extensions.assertj.strings;

import org.assertj.core.api.AbstractCharSequenceAssert;

public class StringAssert extends AbstractCharSequenceAssert<StringAssert, String> {

	Strings strings = Strings.instance();

	public StringAssert(String actual) {
		super(actual, StringAssert.class);
	}

	public StringAssert containsWildcardPattern(CharSequence wildcardstring) {
		strings.assertContainsWildcardPattern(info, actual, wildcardstring);
		return this;
	}

	public StringAssert doesNotContainWildcardPattern(CharSequence wildcardstring) {
		strings.assertNotContainsWildcardPattern(info, actual, wildcardstring);
		return this;
	}

}
