package org.extendedCLI.main.argument;

import java.util.Arrays;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.ArrayUtils;

class DefaultArgument implements Argument {

	private final String name;
	private String longName;
	private final Requires requiresValue;
	private String[] validValues;
	private final String description;
	private String defaultValue;

	DefaultArgument(String name, Requires requiresValue) {
		this(name, requiresValue, new String[0]);
	}

	DefaultArgument(String name, Requires requiresValue, String[] validValues) {
		this(name, requiresValue, "No description provided", validValues);
	}

	DefaultArgument(String name, Requires requiresValue, String description) {
		this(name, requiresValue, description, new String[0]);
	}

	DefaultArgument(String name, Requires requiresValue, String description, String[] validValues) {
		this(name, requiresValue, description, validValues, null);
	}

	DefaultArgument(String name, Requires requiresValue, String description, String[] validValues, String defaultValue) {
		super();
		validateName(name);
		validateRequires(requiresValue);
		this.name = name;
		this.requiresValue = requiresValue;
		this.description = description;
		this.validValues = validValues;
		this.defaultValue = defaultValue;
	}

	private void validateRequires(Requires requires) {
		if(requires == null) {
			throw new IllegalArgumentException();
		}
	}

	private void validateName(String name) {
		if(name == null || name.isEmpty() || name.contains(" ")) {
			throw new IllegalArgumentException(name);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFullName() {
		return "-"+name;
	}

	@Override
	public Requires requiresValue() {
		return requiresValue;
	}

	@Override
	public void setValidValues(String[] validValues) {
		this.validValues = Arrays.stream(validValues).map(String::toUpperCase).toArray(String[]::new);
	}

	@Override
	public boolean isValid(String value) {		
		return validValues == null 
				|| validValues.length == 0 
				|| ArrayUtils.contains(validValues, value.toUpperCase());
	}

	@Override
	public String getDescription() {
		final StringBuilder builder = new StringBuilder(description);
		if(validValues != null && validValues.length > 0) {
			builder.append(" {").append(String.join(";", validValues));
			builder.append("}");
		}
		if(defaultValue != null && !defaultValue.isEmpty()) {
			builder.append(" [").append(defaultValue).append("]");
		}

		return builder.toString();
	}

	@Override
	public String getLongName() {
		return longName;
	}

	@Override
	public void setLongName(String longName) {
		this.longName = longName;
	}

	@Override
	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public Option toOption() {
		final Option.Builder builder;
		builder = Option.builder(getName())
				.desc(getDescription())
				.hasArg(requiresValue != Requires.FALSE)
				.optionalArg(requiresValue != Requires.FALSE && requiresValue == Requires.OPTIONAL);
		
		
		return builder.build(); 
	}

}
