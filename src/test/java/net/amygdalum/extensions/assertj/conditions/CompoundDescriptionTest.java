package net.amygdalum.extensions.assertj.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.description.TextDescription;
import org.junit.jupiter.api.Test;

public class CompoundDescriptionTest {

	@Test
	void testValueForHeadline() throws Exception {
		assertThat(new CompoundDescription(new TextDescription("my headline")).value())
		.isEqualTo("my headline {\n"
			+ "\t\n"
			+ "}");
	}

	@Test
	void testValueForComponents() throws Exception {
		assertThat(new CompoundDescription(new TextDescription("my headline"))
			.addComponent("componentA", new TextDescription("valueA"))
			.addComponent("componentB", new TextDescription("valueB"))
			.value())
		.isEqualTo("my headline {\n"
				+ "\tcomponentA: valueA\n"
				+ "\tcomponentB: valueB\n"
				+ "}");
	}

	@Test
	void testValueForNestedComponents() throws Exception {
		assertThat(new CompoundDescription(new TextDescription("my headline"))
			.addComponent("componentA", new CompoundDescription(new TextDescription("valueA"))
				.addComponent("componentB", new TextDescription("valueB")))
			.value())
		.isEqualTo("my headline {\n"
			+ "\tcomponentA: valueA {\n"
			+ "\t\tcomponentB: valueB\n"
			+ "\t}\n"
			+ "}");
	}
	
}
