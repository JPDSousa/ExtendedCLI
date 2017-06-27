package org.extendedCLI.command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

@SuppressWarnings("javadoc")
public class CLIBuilder {
	
	private final Map<String, Command> commands;
	private final Map<Long, Command> history;
	private InputAdapter input;
	private OutputAdapter output;
	
	public CLIBuilder(final boolean ignoreCase) {
		history = new LinkedHashMap<>();
		commands = createDefaultCommandMap(ignoreCase);
		input = null;
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
		command.setName(name);
		if(input != null) {
			command.setInputAdapter(input);
			command.setOutputAdapter(output);
		}
	}
	
	public void setInput(InputAdapter input) {
		this.input = input;
		commands.values().forEach(c -> c.setInputAdapter(input));
	}
	
	public void setInput(BufferedReader reader) {
		setInput(InputAdapter.fromBufferedReader(reader));
	}
	
	public void setInput(InputStream input) {
		setInput(InputAdapter.fromInputStream(input));
	}
	
	public void setInput(InputStreamReader input) {
		setInput(InputAdapter.fromInputStreamReader(input));
	}
	
	public void setOutput(OutputAdapter output) {
		this.output = output;
		commands.values().forEach(c -> c.setOutputAdapter(output));
	}
	
	public void setOutput(OutputStream output) {
		setOutput(OutputAdapter.fromOutputStream(output));
	}
	
	public void setOutput(PrintStream output) {
		setOutput(OutputAdapter.fromPrintStream(output));
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
