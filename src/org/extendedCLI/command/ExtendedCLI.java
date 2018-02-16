package org.extendedCLI.command;

import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

@SuppressWarnings("javadoc")
public class ExtendedCLI {

	private final CLIBuilder builder;

	ExtendedCLI(final CLIBuilder builder) {
		this.builder = builder;
	}

	public void listen() {
		final InputAdapter input = builder.getInput();
		final OutputAdapter output = builder.getOutput();
		final String promptMessage = builder.getPromptMessage();
		String line;

		try {
			do {
				output.print(promptMessage);
				line = input.readLine();
				if (Objects.nonNull(line)) {
					execute(line);
				}
			} while (Objects.nonNull(line));
		} catch (Throwable e) {
			builder.getCommands().get(Exit.NAME).execute("");
		}
	}

	public void execute(final String line) {
		final Map<String, Command> commands = builder.getCommands();
		final Map<Long, Command> history = builder.getHistory();
		final String name;
		final Command command;

		if(StringUtils.isNotBlank(line)) {
			name = new StringTokenizer(line, " ").nextToken();
			command = commands.get(name);
			if(command == null) {
				builder.getNoSuchCommandHandler().accept(line);
			} else {				
				command.execute(line.substring(name.length()));
				history.put(System.currentTimeMillis(), command);
			}
		}
	}
}
