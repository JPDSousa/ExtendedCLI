package org.extendedCLI.command;

import org.apache.commons.cli.HelpFormatter;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

import static com.google.common.base.Preconditions.*;

@SuppressWarnings("javadoc")
public abstract class AbstractCommand implements Command {

	private final Arguments args;
	protected InputAdapter input;
	protected OutputAdapter output;
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
	public void setInputAdapter(InputAdapter input) {
		checkArgument(input != null, "The input cannot be null");
		this.input = input;
	}

	@Override
	public void setOutputAdapter(OutputAdapter output) {
		checkArgument(output != null, "The output cannot be null");
		this.output = output;
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

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
