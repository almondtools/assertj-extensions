package net.amygdalum.extensions.assertj.conditions;

import org.assertj.core.api.Condition;

public class AString extends Condition<String> {

	public AString(String expected) {
		super(actual -> expected.equals(actual), "matches \"%s\"", expected);
	}

	public static AString matching(String string) {
		return new AString(string);
	}

}
