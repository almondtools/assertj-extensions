package net.amygdalum.extensions.assertj.conditions;

import static java.util.stream.Collectors.joining;

import java.util.LinkedHashMap;
import java.util.Map;

import org.assertj.core.description.Description;

public class CompoundDescription extends Description {

	private Description descriptionHeadline;
	private Map<String, Description> componentDescriptions;

	public CompoundDescription(Description descriptionHeadline) {
		this.descriptionHeadline = descriptionHeadline;
		this.componentDescriptions = new LinkedHashMap<>();
	}

	public CompoundDescription addComponent(String key, Description description) {
		componentDescriptions.put(key, description);
		return this;
	}

	public String indent(String value) {
		return value.replace("\n", "\n\t");
	}

	@Override
	public String value() {
		return descriptionHeadline.value()
			+ componentDescriptions.entrySet().stream()
				.map(entry -> indent(entry.getKey() + ": " + entry.getValue().value()))
				.collect(joining("\n\t", " {\n\t", "\n}"));
	}

}
