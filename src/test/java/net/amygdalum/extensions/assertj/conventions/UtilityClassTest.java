package net.amygdalum.extensions.assertj.conventions;

import static net.amygdalum.extensions.assertj.conventions.UtilityClass.utilityClass;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

public class UtilityClassTest {

	@Test
	public void testMismatchesSafelyNonFinal() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(NotFinal.class))
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("is not declared final");
	}

	@Test
	public void testMismatchesSafelyNoPrivateConstructor() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(NoPrivateConstructor.class))
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("has a non-private default constructor");
	}

	@Test
	public void testMismatchesSafelyPrivateConstructorThrowingCheckedExceptions() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(PrivateConstructorThrowingCheckedExceptions.class))
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("constructor throws checked exceptions");
	}

	@Test
	public void testMismatchesSafelyNonStaticMethods() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(NonStaticMethods.class))
			.isInstanceOf(MultipleFailuresError.class)
			.hasMessageContaining("has a non static method \"method\"");
	}

	@Test
	public void testMatchesSafelyUtilityClass() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(Utility.class)).doesNotThrowAnyException();
	}

	@Test
	public void testMatchesSafelyUtilityClassThrowingRuntimeException() throws Exception {
		assertThatCode(() -> utilityClass().conventions().accept(UtilityThrowingRuntimeException.class)).doesNotThrowAnyException();
	}

	public static class NotFinal {

	}

	public static final class NoPrivateConstructor {

	}

	public static final class NonStaticMethods {

		private NonStaticMethods() {
		}

		public void method() {
		}

	}

	public static final class PrivateConstructorThrowingCheckedExceptions {

		private PrivateConstructorThrowingCheckedExceptions() throws IOException {
			throw new IOException();
		}

	}

	public static final class Utility {

		private Utility() {
		}

		public static void method() {
		}

	}

	public static final class UtilityThrowingRuntimeException {

		private UtilityThrowingRuntimeException() {
			throw new UnsupportedOperationException();
		}

		public static void method() {
		}

	}

}
