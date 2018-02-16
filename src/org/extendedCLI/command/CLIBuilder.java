package org.extendedCLI.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings("javadoc")
public class CLIBuilder {

	private static final String ERR_MSG_NOT_BUILT = "This builder is not built yet.";
	private static final String ERR_MSG_ALREADY_BUILT = "This builder is already built";
	private final Map<String, Command> commands;
	private final Map<Long, Command> history;
	private final List<Runnable> exitPreHooks;

	private Runnable exitAction;
	private Consumer<String> noSuchCommandHandler;

	private InputAdapter input;
	private OutputAdapter output;

	private String promptMessage;

	private boolean built;

	public CLIBuilder(final boolean ignoreCase) {
		built = false;
		history = Maps.newLinkedHashMap();
		commands = createDefaultCommandMap(ignoreCase);
		exitPreHooks = Lists.newArrayList();

		exitAction = () -> System.exit(0);
		noSuchCommandHandler = line -> output.println("No such command: " + line);

		input = InputAdapter.SYSTEM_IN;
		output = OutputAdapter.SYSTEM_OUT;

		promptMessage = ">";
	}

	private Map<String, Command> createDefaultCommandMap(final boolean ignoreCase) {
		final Map<String, Command> commandMap;
		if(ignoreCase) {
			commandMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		}
		else {
			commandMap = new TreeMap<>();
		}

		commandMap.put(Help.NAME, new Help(commandMap));
		commandMap.put(History.NAME, new History(history));
		commandMap.put(Exit.NAME, new Exit(exitPreHooks, () -> exitAction.run()));

		return commandMap;
	}

	public CLIBuilder registerCommand(String name, Command command) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);

		commands.put(name.toUpperCase(), command);
		command.setName(name);
		command.setIoSuppliers(this::getInput, this::getOutput);
		return this;
	}

	public CLIBuilder withExitPreHook(final Runnable preHook) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		this.exitPreHooks.add(preHook);
		return this;
	}

	public CLIBuilder withExitAction(final Runnable exitAction) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		this.exitAction = exitAction;
		return this;
	}

	public CLIBuilder withNoSuchCommandHandler(final Consumer<String> noSuchCommandHandler) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		this.noSuchCommandHandler = noSuchCommandHandler;
		return this;
	}

	public CLIBuilder withInput(InputAdapter input) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		this.input = input;
		return this;
	}

	public CLIBuilder withInput(Reader reader) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		return withInput(InputAdapter.fromReader(reader));
	}

	public CLIBuilder withInput(InputStream input) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		return withInput(InputAdapter.fromInputStream(input));
	}

	public CLIBuilder withOutput(OutputAdapter output) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		this.output = output;
		return this;
	}

	public CLIBuilder withOutput(OutputStream output) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		return withOutput(OutputAdapter.fromOutputStream(output));
	}

	public CLIBuilder withPromptMessage(final String promptMessage) {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		Preconditions.checkArgument(StringUtils.isNotBlank(promptMessage), "Invalid Prompt Message");
		this.promptMessage = promptMessage;
		return this;
	}

	public synchronized ExtendedCLI build() {
		Preconditions.checkState(!built, ERR_MSG_ALREADY_BUILT);
		built = true;
		return new ExtendedCLI(this);
	}

	Map<String, Command> getCommands() {
		Preconditions.checkState(built, ERR_MSG_NOT_BUILT);
		return commands;
	}

	Map<Long, Command> getHistory() {
		Preconditions.checkState(built, ERR_MSG_NOT_BUILT);
		return history;
	}

	String getPromptMessage() {
		Preconditions.checkState(built, ERR_MSG_NOT_BUILT);
		return promptMessage;
	}

	InputAdapter getInput() {
		Preconditions.checkState(built, ERR_MSG_NOT_BUILT);
		return input;
	}

	OutputAdapter getOutput() {
		Preconditions.checkState(built, ERR_MSG_NOT_BUILT);
		return output;
	}

	Consumer<String> getNoSuchCommandHandler() {
		return noSuchCommandHandler;
	}

}
