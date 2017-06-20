package org.extendedCLI.argument;

import org.apache.commons.cli.Option;

/**
 * An object of this type stores all the information relative to an argument. Each argument is represented by
 * one instance of this class. <b>Use the static methods provided by this interface to create new 
 * objects of this type.</b>
 * 
 * Notes on argument conventions:
 * <small>(taken from <a href="http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html">here</a>)
 * <ul>
 * 	<li></li>
 * </ul>
 * 
 * @author Joao Sousa
 *
 */
public interface Argument {

	/**
	 * Creates a new argument with the specified parameters.
	 * 
	 * @param name name of the argument 
	 * @param requiresValue requirement specification of the argument's value (i.e. whether a value is forbidden, required or simply optional)
	 * @return a newly created argument
	 */
	static Argument create(String name, Requires requiresValue) {
		return create(name, requiresValue, new String[0], null);
	}
	
	/**
	 * Creates a new argument with the specified parameters.
	 * 
	 * @param name name of the argument 
	 * @param requiresValue requirement specification of the argument's value (i.e. whether a value is forbidden, required or simply optional)
	 * @param description argument's description. Will be printed on 'help'
	 * @return a newly created argument
	 */
	static Argument create(String name, Requires requiresValue, String description) {
		return create(name, requiresValue, description, new String[0], null);
	}

	/**
	 * Creates a new argument with the specified parameters.
	 * 
	 * @param name name of the argument
	 * @param requiresValue requirement specification of the argument's value (i.e. whether a value is forbidden, required or simply optional)
	 * @param validValues a list of acceptable values for the argument's value
	 * @param defaultValue value that will be assumed if no value is specified. Only acceptable if the value is marked as optional.
	 * @return a newly created argument
	 */
	static Argument create(String name, Requires requiresValue, String[] validValues, String defaultValue) {
		return create(name, requiresValue, "No description provided", validValues, defaultValue);
	}
	
	/**
	 * Creates a new argument with the specified parameters.
	 * 
	 * @param name name of the argument
	 * @param requiresValue requirement specification of the argument's value (i.e. whether a value is forbidden, required or simply optional)
	 * @param description argument's description. Will be printed on 'help'
	 * @param validValues a list of acceptable values for the argument's value
	 * @param defaultValue value that will be assumed if no value is specified. Only acceptable if the value is marked as optional.
	 * @return a newly created argument
	 */
	static Argument create(String name, Requires requiresValue, String description, String[] validValues, String defaultValue) {
		return new DefaultArgument(name, requiresValue, description, validValues, defaultValue);
	}
	
	/**
	 * Returns the name of the argument
	 * 
	 * @return name of the argument
	 */
	String getName();
	
	
	/**
	 * Returns the requirement specification of this argument's value
	 * 
	 * @return requirement specification of this argument's value
	 */
	Requires requiresValue();
	
	/**
	 * Returns the argument's description
	 * 
	 * @return argument's description
	 */
	String getDescription();

	/**
	 * Runs the {@code value} against the valid values.
	 * <p>
	 * Note that:
	 * <ul>
	 * <li>This method returns case sensitive results</li>
	 * <li>If the {@code value} is {@code null}, this method returns {@code false}</li>
	 * <li>If no valid values are specified, this method returns {@code true}</li>
	 * </ul>
	 * </p>
	 * @param value value to test against the valid values
	 * @return {@code true} if the value meets the requirements and {@code false} otherwise.
	 */
	boolean isValid(String value);
	/**
	 * (Re)sets the valid values for this argument's value. Any already existent set will be overwritten.
	 * @param values new valid values for this argument's value
	 */
	void setValidValues(String[] values);

	/**
	 * (Re)sets the default value for this argument. Only valid if the requirement specification is marked as optional.
	 * 
	 * @param value new default value
	 */
	void setDefaultValue(String value);
	/**
	 * Returns the default value for this argument's value. 
	 * 
	 * @return the default value. Returns {@code null} is no value is specified.
	 */
	String getDefaultValue();

	/**
	 * Returns a formatted version of this arguments name with an additional '-' before the name.
	 * 
	 * @return a formatted version of the name.
	 */
	String getFormattedName();

	/**
	 * Converts this argument into an {@link Option} object. Useful when using the Apache Commons CLI framework.
	 * 
	 * @return an {@link Option} object of this argument.
	 */
	Option toOption();

	/**
	 * Sets an alternative name for the argument. Usually, arguments are defined only by one or two characters.
	 * However, sometimes one might want to specify an alternative longer name for the argument.
	 * 
	 * @param longName long name for the argument
	 */
	void setLongName(String longName);

	/**
	 * Returns the long name for this argument. If no name is specified, {@code null} is returned.
	 * 
	 * @return the long name or {@code null}.
	 */
	String getLongName();

	/**
	 * Returns the valid values.
	 * 
	 * @return valid values
	 */
	String[] getValidValues();
	
}
