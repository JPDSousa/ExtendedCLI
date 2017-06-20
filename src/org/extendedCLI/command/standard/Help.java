package org.extendedCLI.command.standard;

import java.util.Map;

import org.apache.commons.cli.HelpFormatter;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class Help extends AbstractCommand {
	
	private Map<String, Command> commands;
	
	public Help(Map<String, Command> commands) {
		super(Arguments.empty(), "Print the command list documentation.");
		this.commands = commands;
	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		final HelpFormatter help = new HelpFormatter();
		commands.values().forEach(c -> help.printHelp(c.getSyntax(), c.getDescription(),  c.getArgs().toOptions(), ""));
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
