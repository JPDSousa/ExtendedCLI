package org.extendedCLI.exceptions;

@SuppressWarnings("javadoc")
public final class NoSuchCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchCommandException(String commandName) {
		super("Command syntax for command " + commandName + "%s is invalid.");
	}

}
