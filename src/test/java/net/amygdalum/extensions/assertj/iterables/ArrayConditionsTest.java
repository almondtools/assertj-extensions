package net.amygdalum.extensions.assertj.iterables;

import static net.amygdalum.extensions.assertj.conventions.UtilityClass.utilityClass;
import static net.amygdalum.extensions.assertj.iterables.ArrayConditions.containingExactly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

import java.util.Objects;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

public class ArrayConditionsTest {

	private static final Condition<Object> IS_NULL = new Condition<>(Objects::isNull, "is null");
	private static final Condition<Object> NON_NULL = new Condition<>(Objects::nonNull, "is not null");

	@Test
	public void testArrayConditions() throws Exception {
		assertThat(ArrayConditions.class).satisfies(utilityClass().conventions());
	}
	
	@Test
	public void testContainsExactly() throws Exception {
		assertThat(new Object[] {new Object(), null}).is(containingExactly(NON_NULL, IS_NULL));
		assertThat(new Object[] {new Object(), null}).is(not(containingExactly(NON_NULL)));
		assertThat(new Object[] {new Object(), null}).is(not(containingExactly(IS_NULL)));
		assertThat(new Object[] {new Object(), null}).is(not(containingExactly(IS_NULL, NON_NULL)));
		assertThat(new Object[] {new Object(), null}).is(not(containingExactly(NON_NULL, NON_NULL)));
		assertThat(new Object[] {new Object(), null}).is(not(containingExactly(IS_NULL, IS_NULL)));
		assertThat(new Object[] {new Object()}).is(not(containingExactly(NON_NULL, IS_NULL)));
		assertThat(new Object[] {null}).is(not(containingExactly(NON_NULL, IS_NULL)));
	}
	
}
