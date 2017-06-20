package org.extendedCLI.argument;

/**
 * This interface is used as a wrapper for enumerations ({@link Enum}. For code readability and simplicity, we design this interface so that developers
 * can use <b>enums</b> to represent arguments.
 * 
 * <p> Furthermore, this interface provides the concept of group. A group represents a subset of arguments in which only one argument per group is allowed
 * in the same instruction.
 * 
 * @author Joao
 *
 */
public interface ArgumentEnum {
	
	/**
	 * Returns the argument wrapped by this interface.
	 * 
	 * @return argument wrapped by this interface.
	 */
	Argument getArgument();
	
	/**
	 * Returns the group id of the argument wrapped by this interface.
	 * 
	 * @return group id of the argument wrapped by this interface.
	 */
	int getGroupID();

}
