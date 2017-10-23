package org.extendedCLI.argument;

import org.apache.commons.cli.Option;
import org.apache.commons.lang3.ArrayUtils;

class DefaultArgument implements Argument {

	private final String name;
	private String longName;
	private final Requires requiresValue;
	private String[] validValues;
	private final String description;
	private String defaultValue;

	DefaultArgument(String name, Requires requiresValue, String description, String[] validValues, String defaultValue) {
		super();
		checkValidName(name);
		checkValidRequires(name, requiresValue);
		checkValidDescription(name, description);
		checkValidValues(name, validValues);
		checkValidDefaultValue(name, validValues, defaultValue);
		this.name = name;
		this.requiresValue = requiresValue;
		this.description = description;
		this.validValues = validValues;
		this.defaultValue = defaultValue;
	}

	private void checkValidValues(String name, String[] values) {
		if(values == null) {
			throw new IllegalArgumentException("The valid values for " + name + " cannot be null.");
		}
	}

	private void checkValidDefaultValue(String name, String[] validValues, String defaultValue) {
		if(!isValid(defaultValue, validValues)) {
			throw new IllegalArgumentException("The default value '" + defaultValue + "' is not valid for argument " + name);
		}
	}

	private void checkValidDescription(String name, String description) {
		if(description == null) {
			throw new IllegalArgumentException("The description for argument " + name + " cannot be null");
		}
	}

	private void checkValidRequires(String name, Requires requires) {
		if(requires == null) {
			throw new IllegalArgumentException("The requirement specification for argument " + name + " cannot be null");
		}
	}

	private void checkValidName(String name) {
		if(name == null || name.isEmpty() || name.contains(" ")) {
			throw new IllegalArgumentException(name + " is not a valid name for an argument.");
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFormattedName() {
		return "-"+name;
	}

	@Override
	public Requires requiresValue() {
		return requiresValue;
	}

	@Override
	public void setValidValues(String[] validValues) {
		this.validValues = validValues;
	}
	
	@Override
	public String[] getValidValues() {
		return validValues;
	}

	@Override
	public boolean isValid(String value) {
		return isValid(value, validValues);
	}
	
	private boolean isValid(String value, String[] validValues) {
		return validValues.length == 0 || ( value != null && ArrayUtils.contains(validValues, value));
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
		checkValidDefaultValue(getName(), validValues, value);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DefaultArgument other = (DefaultArgument) obj;

		return name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return new StringBuilder(getName())
				.append("{")
				.append(requiresValue())
				.append("}")
				.toString();
		
	}

}
