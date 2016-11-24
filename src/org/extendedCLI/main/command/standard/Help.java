package org.extendedCLI.main.command.standard;

import java.util.Map;

import org.apache.commons.cli.HelpFormatter;
import org.extendedCLI.main.argument.Arguments;
import org.extendedCLI.main.command.AbstractCommand;
import org.extendedCLI.main.command.Command;
import org.extendedCLI.main.command.ExtendedCommandLine;

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
