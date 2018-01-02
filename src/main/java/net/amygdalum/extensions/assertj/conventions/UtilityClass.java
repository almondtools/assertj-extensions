package net.amygdalum.extensions.assertj.conventions;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.assertj.core.api.SoftAssertions;

public class UtilityClass {

	public UtilityClass() {
	}

	public static UtilityClass utilityClass() {
		return new UtilityClass();
	}

	public Consumer<Class<?>> conventions() {
		return new Conventions();
	}

	private class Conventions implements Consumer<Class<?>> {

		private SoftAssertions softly;

		public Conventions() {
			softly = new SoftAssertions();
		}

		@Override
		public void accept(Class<?> item) {
			softly.assertThat(isFinal(item.getModifiers())).withFailMessage("%s is not declared final", item.getName()).isTrue();
			try {
				Constructor<?> constructor = item.getDeclaredConstructor();
				softly.assertThat(constructor.isAccessible()).withFailMessage("%s has an accessible constructor", item.getName()).isFalse();
				softly.assertThat(isPrivate(constructor.getModifiers())).withFailMessage("%s has a non-private default constructor", item.getName()).isTrue();
				constructor.setAccessible(true);
				constructor.newInstance();
			} catch (InvocationTargetException e) {
				if (!(e.getCause() instanceof RuntimeException)) {
					softly.fail("%s's constructor throws checked exceptions: %s", item.getName(), e.getTargetException());
				}
			} catch (ReflectiveOperationException e) {
				softly.fail("%s's constructor cannot be invoked reflectively: %s", item.getName(), e);
			}
			for (Method method : item.getDeclaredMethods()) {
				softly.assertThat(isStatic(method.getModifiers())).withFailMessage("%s has a non static method \"%s\"", item.getName(), method.getName()).isTrue();
			}
			softly.assertAll();
		}
	}

}
