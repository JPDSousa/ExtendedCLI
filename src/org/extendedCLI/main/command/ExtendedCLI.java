package org.extendedCLI.main.command;

import java.util.Map;
import java.util.StringTokenizer;

import org.extendedCLI.main.exceptions.NoSuchCommandException;

@SuppressWarnings("javadoc")
public class ExtendedCLI {

	private final Map<String, Command> commands;
	protected final Map<Long, Command> history;

	ExtendedCLI(final CLIBuilder builder) {
		this.commands = builder.getCommands();
		this.history = builder.getHistory();
	}

	public void execute(String line) throws NoSuchCommandException {
		final String name;
		final Command command;

		if(line != null && !line.isEmpty()) {
			name = new StringTokenizer(line, " ").nextToken();
			command = commands.get(name.toUpperCase());
			if(command == null) {
				throw new NoSuchCommandException(name);
			}
			command.execute(line.substring(name.length()));
			history.put(System.currentTimeMillis(), command);
		}
	}
}
