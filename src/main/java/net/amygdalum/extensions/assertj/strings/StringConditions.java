package net.amygdalum.extensions.assertj.strings;

import org.assertj.core.api.Condition;

public final class StringConditions {
	
	private StringConditions() {
	}

	public static Condition<String> containing(String string) {
		return new Condition<>(actual -> actual.contains(string), "contains string \"%s\"", string);
	}

	public static Condition<String> containingWildcardPattern(String pattern) {
		return new Condition<>(actual -> Strings.containsWildcardPattern(actual, pattern), "contains wildcard pattern \"%s\"", pattern);
	}
}
