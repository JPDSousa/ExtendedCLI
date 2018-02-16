package org.extendedCLI.command.standard;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.extendedCLI.ioAdapters.OutputAdapter;

@SuppressWarnings("javadoc")
public class History extends AbstractCommand {

	public static final String NAME = "HISTORY";
	public static final String DESCRIPTION = "Prints the execution history with timestamps";
	public static final String NO_COMMANDS_STRING = "No commands yet.";

	private final Map<Long, Command> history;

	public History(final Map<Long, Command> history) {
		super(Arguments.empty(), DESCRIPTION);
		this.history = history;
	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		final OutputAdapter output = getOutput();
		if(history.isEmpty()) {
			output.println(NO_COMMANDS_STRING);
		}
		else {
			for(long date : history.keySet()) {
				output.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()) + " -> " + history.get(date));
			}
		}
		output.flush();
	}

	@Override
	public void undo() {
		throw new UnsupportedOperationException("This command involves printing values, which cannot be undone");
	}
}
