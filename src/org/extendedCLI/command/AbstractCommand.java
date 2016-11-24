package org.extendedCLI.command;

import java.io.BufferedReader;

import org.apache.commons.cli.HelpFormatter;
import org.extendedCLI.argument.Arguments;

@SuppressWarnings("javadoc")
public abstract class AbstractCommand implements Command {

	private final Arguments args;
	protected BufferedReader reader;
	private String description;
	private String name;

	protected AbstractCommand(Arguments args) {
		this(args, "No description provided.");
	}

	protected AbstractCommand(Arguments args, String description) {
		this(args, "", description);
		name = this.getClass().getSimpleName();
	}

	protected AbstractCommand(Arguments args, String name, String description) {
		this.name = name;
		this.description = description;
		this.args = args;
	}

	@Override
	public final void execute(String line) {
		ExtendedCommandLine commandLine = args.validate(line);
		if (commandLine == null) {
			new HelpFormatter().printHelp(getSyntax(), getDescription(),  getArgs().toOptions(), "");
		} else {
			execute(commandLine);
		}
	}

	@Override
	public abstract void undo();

	@Override
	public void setInputReader(BufferedReader inputreader) {
		this.reader = inputreader;
	}

	protected abstract void execute(ExtendedCommandLine commandLine);

	@Override
	public Arguments getArgs() {
		return args;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getSyntax() {
		final StringBuilder builder = new StringBuilder(getName());
		builder.append(" ").append(args.getSyntax());

		return builder.toString();
	}
}
