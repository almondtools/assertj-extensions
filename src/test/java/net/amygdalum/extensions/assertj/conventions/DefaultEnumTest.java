package net.amygdalum.extensions.assertj.conventions;

import static net.amygdalum.extensions.assertj.conventions.DefaultEnum.defaultEnum;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

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
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("Object is not an enum");
	}

	@Test
	public void testMatchesSafelyFailsWithElements() throws Exception {
		assertThatCode(() -> defaultEnum().withElements(1).conventions().accept(MyEnum.class))
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("should have 1 elements");
	}

	private static enum MyEnum {
		V1, V2;
	}

}
