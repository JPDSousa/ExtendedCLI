package org.extendedCLI.command.standard;

import java.util.List;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class Exit extends AbstractCommand {

	public static final String NAME = "EXIT";
	public static final String DESCRIPTION = "Exits the system gracefully";
	
	private final List<Runnable> preHooks;
	private final Runnable exitAction;
	
	public Exit(final List<Runnable> preHooks, final Runnable exitAction) {
		super(Arguments.empty(), DESCRIPTION);
		this.preHooks = preHooks;
		this.exitAction = exitAction;
	}

	@Override
	public void undo() {
		throw new UnsupportedOperationException("Cannot undo an exit");
	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		preHooks.forEach(Runnable::run);
		exitAction.run();
	}

}
