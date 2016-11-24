package org.extendedCLI.command;

import java.util.Map;
import java.util.StringTokenizer;

import org.extendedCLI.exceptions.NoSuchCommandException;

@SuppressWarnings("javadoc")
public class ExtendedCLI {

	private final Map<String, Command> commands;
	protected final Map<Long, Command> history;

	ExtendedCLI(Map<String, Command> commands, Map<Long, Command> history) {
		this.commands = commands;
		this.history = history;
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
