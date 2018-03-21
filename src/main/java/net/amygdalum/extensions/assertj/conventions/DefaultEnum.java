package net.amygdalum.extensions.assertj.conventions;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.assertj.core.api.SoftAssertions;

public class DefaultEnum {

	private Integer count;

	public DefaultEnum() {
	}

	public static DefaultEnum defaultEnum() {
		return new DefaultEnum();
	}

	public DefaultEnum withElements(int count) {
		this.count = count;
		return this;
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
			softly.assertThat(Enum.class).withFailMessage("%s is not an enum", item.getName()).isAssignableFrom(item);

			if (item.isEnum()) {
				Enum<?>[] enumConstants = (Enum<?>[]) item.getEnumConstants();

				if (count != null) {
					softly.assertThat(enumConstants).withFailMessage("%s should have %s elements", item.getName(), count).hasSize(count);
				}
				for (Enum<?> enumConstant : enumConstants) {
					try {
						Method valueOf = item.getDeclaredMethod("valueOf", String.class);
						valueOf.setAccessible(true);
						Object result = valueOf.invoke(null, enumConstant.name());
						softly.assertThat(result).withFailMessage("valueOf(\"" + enumConstant.name() + "\") != " + enumConstant.getClass().getSimpleName() + "." + enumConstant.name())
							.isSameAs(enumConstant);
					} catch (ReflectiveOperationException | SecurityException e) {
						softly.fail("fails with exception", e);
					}
				}
			}
			softly.assertAll();
		}
	}

}
