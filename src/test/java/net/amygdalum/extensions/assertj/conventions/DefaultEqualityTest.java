package net.amygdalum.extensions.assertj.conventions;

import static net.amygdalum.extensions.assertj.conventions.DefaultEquality.defaultEquality;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

public class DefaultEqualityTest {

	@Test
	public void testMatchesSafely() throws Exception {
		assertThatCode(() -> defaultEquality().conventions().accept(new DefaultEqual())).doesNotThrowAnyException();
	}

	@Test
	public void testMatchesSafelyWithConstraints() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new DefaultEqual("field"))
			.andNotEqualTo(new DefaultEqual("otherfield"))
			.conventions().accept(new DefaultEqual("field")))
				.doesNotThrowAnyException();
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraints() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new BrokenEqual("field"))
			.conventions().accept(new BrokenEqual("field")))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("field should equal field");
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraintsAsymetric() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new BrokenEqualAsymetric(false))
			.conventions().accept(new BrokenEqualAsymetric(true)))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("false should equal true");
	}

	@Test
	public void testMismatchesSafelyWithNonEqualConstraints() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andNotEqualTo(new BrokenNotEqual("field"))
			.conventions().accept(new BrokenNotEqual("field")))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("field should not equal field");
	}

	@Test
	public void testMismatchesSafelyWithNonEqualConstraintsAsymetric() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andNotEqualTo(new BrokenEqualAsymetric(true))
			.conventions().accept(new BrokenEqualAsymetric(false)))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("true should not equal false");
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraintsToString() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new DefaultEqualNoToString("field"))
			.includingToString()
			.conventions().accept(new DefaultEqualNoToString("field")))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageMatching("[\\s\\S]*toString of \\d+ should be equal to toString of \\d+[\\s\\S]*");
	}

	@Test
	public void testMatchesSafelyWithEqualConstraintsToString() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new DefaultEqualToString("field"))
			.includingToString()
			.conventions().accept(new DefaultEqualToString("field")))
				.doesNotThrowAnyException();
	}

	@Test
	public void testMatchesSafelyWithNonEqualConstraints() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andNotEqualTo(new DefaultEqual("field2"))
			.conventions().accept(new DefaultEqual("field1")))
				.doesNotThrowAnyException();
	}

	@Test
	public void testMismatchesNull() throws Exception {
		assertThatCode(() -> defaultEquality()
			.conventions().accept(null))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("equality on null is not valid");
	}

	@Test
	public void testMismatchesNotSelfEqual() throws Exception {
		assertThatCode(() -> defaultEquality()
			.conventions().accept(new NotSelfEqual()))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("should equal itself");
	}

	@Test
	public void testMismatchesSelfEqualButDifferentHashcodes() throws Exception {
		assertThatCode(() -> defaultEquality()
			.conventions().accept(new SelfEqualButDifferentHashcodes()))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("hashcode should be idempotent");
	}

	@Test
	public void testMismatchesOnDifferentHashcodes() throws Exception {
		assertThatCode(() -> defaultEquality()
			.andEqualTo(new DefaultEqualButDifferentHashcodes("field", 1))
			.conventions().accept(new DefaultEqualButDifferentHashcodes("field", 2)))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("hashcode of field2 should be equal to hashcode of field1");
	}

	@Test
	public void testMismatchesNullEqual() throws Exception {
		assertThatCode(() -> defaultEquality()
			.conventions().accept(new NullEqual()))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("should not equal null");
	}

	@Test
	public void testMismatchesEqualToOtherClasses() throws Exception {
		assertThatCode(() -> defaultEquality()
			.conventions().accept(new EqualToOtherClasses()))
				.isInstanceOf(MultipleFailuresError.class)
				.hasMessageContaining("should not match an arbitrary object");
	}

	private static class NullEqual {

		@Override
		public boolean equals(Object obj) {
			return true;
		}
	}

	private static class EqualToOtherClasses {

		@Override
		public boolean equals(Object obj) {
			return obj != null;
		}
	}

	private static class NotSelfEqual {

		@Override
		public boolean equals(Object obj) {
			return false;
		}
	}

	private static class SelfEqualButDifferentHashcodes {

		private static int no;

		@Override
		public int hashCode() {
			return no++;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}
	}

	private static class DefaultEqual {
		protected String field;

		public DefaultEqual() {
		}

		public DefaultEqual(String field) {
			this.field = field;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			DefaultEqual other = (DefaultEqual) obj;
			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
		}

	}

	private static class DefaultEqualButDifferentHashcodes {
		protected String field;
		private int hashCode;

		public DefaultEqualButDifferentHashcodes(String field, int hashCode) {
			this.field = field;
			this.hashCode = hashCode;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			DefaultEqualButDifferentHashcodes other = (DefaultEqualButDifferentHashcodes) obj;
			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return field + hashCode;
		}
	}

	private static class BrokenEqual {
		protected String field;

		public BrokenEqual(String field) {
			this.field = field;
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class BrokenEqualAsymetric {
		protected boolean field;

		public BrokenEqualAsymetric(boolean field) {
			this.field = field;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return field;
		}

		@Override
		public String toString() {
			return "" + field;
		}
	}

	private static class BrokenNotEqual {
		protected String field;

		public BrokenNotEqual(String field) {
			this.field = field;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class DefaultEqualToString extends DefaultEqual {

		public DefaultEqualToString(String field) {
			super(field);
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class DefaultEqualNoToString extends DefaultEqual {

		public DefaultEqualNoToString(String field) {
			super(field);
		}

		@Override
		public String toString() {
			return "" + System.identityHashCode(this);
		}
	}

}
