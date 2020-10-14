package net.amygdalum.extensions.assertj.conventions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.api.SoftAssertions;

public class DefaultEquality {

	private static final Object differentObject = new Object();

	private List<Object> equals;
	private List<Object> notEquals;
	private boolean toString;

	public DefaultEquality() {
		this.equals = new ArrayList<>();
		this.notEquals = new ArrayList<>();
		this.toString = false;
	}

	public static DefaultEquality defaultEquality() {
		return new DefaultEquality();
	}

	public DefaultEquality andEqualTo(Object element) {
		this.equals.add(element);
		return this;
	}

	public DefaultEquality andNotEqualTo(Object element) {
		this.notEquals.add(element);
		return this;
	}

	public DefaultEquality includingToString() {
		this.toString = true;
		return this;
	}

	public <T> Consumer<T> conventions() {
		return new Conventions<>();
	}

	private class Conventions<T> implements Consumer<T> {

		private SoftAssertions softly;

		public Conventions() {
			softly = new SoftAssertions();
		}
		
		@Override
		public void accept(T item) {
			if (item == null) {
				softly.fail("equality on null is not valid");
				softly.assertAll();
				return;
			}

			softly.assertThat(item.equals(item)).as("object %s should equal itself", item).isTrue();
			softly.assertThat(item.hashCode()).as("objects %s hashcode should be idempotent", item).isEqualTo(item.hashCode());

			for (Object element : equals) {
				softly.assertThat(item).as("%s should equal %s", item, element).isEqualTo(element);
				softly.assertThat(element).as("%s should equal %s", element, item).isEqualTo(item);

				softly.assertThat(item.hashCode()).as("hashcode of %s should be equal to hashcode of %s", item, element).isEqualTo(element.hashCode());

				if (toString) {
					softly.assertThat(item.toString()).as("toString of %s should be equal to toString of %s", item, element).isEqualTo(element.toString());
				}
			}

			softly.assertThat(item.equals(null)).as("%s is not null and thus should not equal null", item).isFalse();
			softly.assertThat(item).as("%s should not match an arbitrary object", item).isNotEqualTo(differentObject);

			for (Object element : notEquals) {
				softly.assertThat(item).as("%s should not equal %s", item, element).isNotEqualTo(element);
				softly.assertThat(element).as("%s should not equal %s", element, item).isNotEqualTo(item);

				element.hashCode();

				if (toString) {
					element.toString();
				}
			}
			softly.assertAll();
		}
	}

}
