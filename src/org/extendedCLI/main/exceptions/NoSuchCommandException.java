package org.extendedCLI.main.exceptions;

@SuppressWarnings("javadoc")
public final class NoSuchCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchCommandException(String commandName) {
		super("Command sintax for command " + commandName + "%s is invalid.");
	}

}
