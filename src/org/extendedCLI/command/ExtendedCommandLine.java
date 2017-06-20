package org.extendedCLI.command;

import org.apache.commons.cli.CommandLine;
import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.ArgumentEnum;

@SuppressWarnings("javadoc")
public class ExtendedCommandLine {
	
	private final CommandLine commandLine;

	public ExtendedCommandLine(CommandLine commandLine) {
		super();
		this.commandLine = commandLine;
	}
	
	public boolean hasArg(Argument argument) {
		return commandLine.hasOption(argument.getName());
	}
	
	public boolean hasArg(ArgumentEnum enumValue) {
		return hasArg(enumValue.getArgument());
	}
	
	public String getValue(Argument argument) {
		return commandLine.getOptionValue(argument.getName(), argument.getDefaultValue());
	}
	
	public String getValue(ArgumentEnum enumValue) {
		return getValue(enumValue.getArgument());
	}

}
