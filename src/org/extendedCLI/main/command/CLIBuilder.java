package org.extendedCLI.main.command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.extendedCLI.main.command.standard.Exit;
import org.extendedCLI.main.command.standard.Help;
import org.extendedCLI.main.command.standard.History;

@SuppressWarnings("javadoc")
public class CLIBuilder {
	
	private final Map<String, Command> commands;
	private final Map<Long, Command> history;
	private BufferedReader inputReader;
	
	public CLIBuilder(final boolean ignoreCase) {
		history = new LinkedHashMap<>();
		commands = createDefaultCommandMap(ignoreCase);
		inputReader = null;
	}
	
	private Map<String, Command> createDefaultCommandMap(final boolean ignoreCase) {
		final Map<String, Command> commandMap;
		if(ignoreCase) {
			commandMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		}
		else {
			commandMap = new TreeMap<>();
		}
		
		commandMap.put("HELP", new Help(commandMap));
		commandMap.put("HISTORY", new History(history));
		commandMap.put("EXIT", new Exit());
		
		return commandMap;
	}

	public void registerCommand(String name, Command command) {
		commands.put(name.toUpperCase(), command);
		if(inputReader != null) {
			command.setInputReader(inputReader);
		}
	}
	
	public void setInputReader(BufferedReader inputReader) {
		this.inputReader = inputReader;
		commands.values().forEach(c -> c.setInputReader(inputReader));
	}
	
	public void setInputReader(InputStream stream) {
		setInputReader(new BufferedReader(new InputStreamReader(stream)));
	}
	
	public ExtendedCLI build() {
		return new ExtendedCLI(this);
	}

	Map<String, Command> getCommands() {
		return commands;
	}

	Map<Long, Command> getHistory() {
		return history;
	}
}
