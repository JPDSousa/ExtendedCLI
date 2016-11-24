package org.extendedCLI.main.argument;

import org.apache.commons.cli.Option;

@SuppressWarnings("javadoc")
public interface Argument {

	static Argument create(String name, Requires requiresValue) {
		return new DefaultArgument(name, requiresValue);
	}
	
	static Argument create(String name, Requires requiresValue, String[] validValues) {
		return new DefaultArgument(name, requiresValue, validValues);
	}
	
	static Argument create(String name, Requires requiresValue, String description) {
		return new DefaultArgument(name, requiresValue, description);
	}
	
	static Argument create(String name, Requires requiresValue, String description, String[] validValues) {
		return new DefaultArgument(name, requiresValue, description, validValues);
	}
	
	static Argument create(String name, Requires requiresValue, String description, String[] validValues, String defaultValue) {
		return new DefaultArgument(name, requiresValue, description, validValues, defaultValue);
	}
	
	String getName();
	
	Requires requiresValue();
	
	String getDescription();

	boolean isValid(String value);
	void setValidValues(String[] values);

	void setDefaultValue(String value);
	String getDefaultValue();

	String getFullName();

	Option toOption();

	void setLongName(String longName);

	String getLongName();
	
}
