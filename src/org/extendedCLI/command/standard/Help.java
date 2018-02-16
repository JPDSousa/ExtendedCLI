package org.extendedCLI.command.standard;

import java.util.Map;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class Help extends AbstractCommand {
	
	public static final String NAME = "HELP";
	public static final String DESCRIPTION = "Print the command list documentation.";
	
	private Map<String, Command> commands;
	
	public Help(Map<String, Command> commands) {
		super(Arguments.empty(), DESCRIPTION);
		this.commands = commands;
	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		commands.values().forEach(Command::help);
	}

	@Override
	public void undo() {
		throw new UnsupportedOperationException("This command involves printing values, which cannot be undone.");
	}

}
