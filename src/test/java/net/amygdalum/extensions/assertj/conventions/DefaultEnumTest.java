package net.amygdalum.extensions.assertj.conventions;

import static net.amygdalum.extensions.assertj.conventions.DefaultEnum.defaultEnum;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.assertj.core.api.SoftAssertionError;
import org.junit.jupiter.api.Test;

public class DefaultEnumTest {

	@Test
	public void testMatchesSafely() throws Exception {
		assertThatCode(() -> defaultEnum().conventions().accept(MyEnum.class)).doesNotThrowAnyException();
	}

	@Test
	public void testMatchesSafelyWithElements() throws Exception {
		assertThatCode(() -> defaultEnum().withElements(2).conventions().accept(MyEnum.class)).doesNotThrowAnyException();
	}

	@Test
	public void testMatchesSafelyFails() throws Exception {
		assertThatCode(() -> defaultEnum().conventions().accept(Object.class))
			.isInstanceOf(SoftAssertionError.class)
			.hasMessageContaining("Object is not an enum");
	}

	@Test
	public void testMatchesSafelyFailsWithElements() throws Exception {
		assertThatCode(() -> defaultEnum().withElements(1).conventions().accept(MyEnum.class))
			.isInstanceOf(SoftAssertionError.class)
			.hasMessageContaining("should have 1 elements");
	}

	private static enum MyEnum {
		V1, V2;
	}

}
