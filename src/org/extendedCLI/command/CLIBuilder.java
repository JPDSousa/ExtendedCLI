package org.extendedCLI.command;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;

@SuppressWarnings("javadoc")
public class CLIBuilder {
	
	private final Map<String, Command> commands;
	private final Map<Long, Command> history;
	private BufferedReader inputReader;
	
	public CLIBuilder() {
		history = new LinkedHashMap<>();
		commands = createDefaultCommandMap();
		inputReader = null;
	}
	
	private Map<String, Command> createDefaultCommandMap() {
		final Map<String, Command> commandMap = new LinkedHashMap<>();
		commandMap.put("HELP", new Help(commandMap));
		commandMap.put("History", new History(history));
		
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
	
	public ExtendedCLI create() {
		return new ExtendedCLI(commands, history);
	}

}
