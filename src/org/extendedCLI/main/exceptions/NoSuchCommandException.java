package org.extendedCLI.main.exceptions;

import exceptthis.core.AbsCustomException;

@SuppressWarnings("javadoc")
public final class NoSuchCommandException extends AbsCustomException {

	private static final long serialVersionUID = 1L;

	public NoSuchCommandException(String commandName) {
		super("Command Error.", "Command sintax for command %s is invalid.", commandName);
	}

}
