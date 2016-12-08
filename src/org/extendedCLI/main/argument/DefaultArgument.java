package org.extendedCLI.main.argument;

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
		validateName(name);
		validateRequires(requiresValue);
		validateDescription(description);
		validateDefaultValue(validValues, defaultValue);
		this.name = name;
		this.requiresValue = requiresValue;
		this.description = description;
		this.validValues = validValues;
		this.defaultValue = defaultValue;
	}

	private static void validateDefaultValue(String[] validValues, String defaultValue) {
		if(((validValues == null || validValues.length == 0) && defaultValue != null) || (defaultValue != null && !ArrayUtils.contains(validValues, defaultValue))) {
			throw new IllegalArgumentException();
		}
	}

	private static void validateDescription(String description) {
		if(description == null) {
			throw new IllegalArgumentException();
		}
	}

	private static void validateRequires(Requires requires) {
		if(requires == null) {
			throw new IllegalArgumentException();
		}
	}

	private static void validateName(String name) {
		if(name == null || name.isEmpty() || name.contains(" ")) {
			throw new IllegalArgumentException(name);
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
		return value != null 
				&& validValues != null 
				&& validValues.length > 0 
				&& ArrayUtils.contains(validValues, value);
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
		validateDefaultValue(validValues, value);
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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
