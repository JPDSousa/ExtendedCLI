package org.extendedCLI.command.standard;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class Exit extends AbstractCommand {

	public Exit() {
		super(Arguments.empty(), "Exits the system gracefully");
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		System.exit(0);
	}

}
