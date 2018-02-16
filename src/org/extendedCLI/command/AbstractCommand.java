package org.extendedCLI.command;

import org.apache.commons.cli.HelpFormatter;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

import com.google.common.base.Preconditions;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public abstract class AbstractCommand implements Command {

	private final HelpFormatter helpFormatter;
	private final Arguments args;
	
	private Supplier<InputAdapter> inputSupplier;
	private Supplier<OutputAdapter> outputSupplier;
	private String description;
	private String name;

	protected AbstractCommand(final Arguments args) {
		this(args, "No description provided.");
	}

	protected AbstractCommand(final Arguments args,	final String description) {
		this(args, "", description);
		name = this.getClass().getSimpleName();
	}

	protected AbstractCommand(final Arguments args,	final String name, final String description) {
		this.helpFormatter = new HelpFormatter();
		this.name = name;
		this.description = description;
		this.args = args;
	}
	
	@Override
	public final void setIoSuppliers(final Supplier<InputAdapter> inputSupplier, 
			final Supplier<OutputAdapter> outputSupplier) {
		Preconditions.checkState(Objects.isNull(this.inputSupplier), "Input supplier is already set");
		Preconditions.checkState(Objects.isNull(this.outputSupplier), "Output supplier is already set");
		Preconditions.checkArgument(Objects.nonNull(inputSupplier), "Input supplier cannot be null");
		Preconditions.checkArgument(Objects.nonNull(outputSupplier), "Output supplier cannot be null");
		this.inputSupplier = inputSupplier;
		this.outputSupplier = outputSupplier;
	}

	@Override
	public final void execute(String line) {
		final ExtendedCommandLine commandLine = args.validate(line);
		if (commandLine == null) {
			help();
		} else {
			execute(commandLine);
		}
	}
	
	protected final InputAdapter getInput() {
		return inputSupplier.get();
	}
	
	protected final OutputAdapter getOutput() {
		return outputSupplier.get();
	}

	@Override
	public abstract void undo();
	
	@Override
	public void help() {
		final OutputAdapter output = getOutput();
		final PrintWriter printWriter = output.asPrintWriter();
		
		helpFormatter.printHelp(printWriter, 
				helpFormatter.getWidth(), getSyntax(), 
				getDescription(), getArgs().toOptions(), 
				helpFormatter.getLeftPadding(), helpFormatter.getDescPadding(), "");
		output.flush();
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
		final String argumentsSyntax = args.getSyntax();
		final StringBuilder builder = new StringBuilder(getName());
		if(!argumentsSyntax.isEmpty()) {
			builder.append(" ").append(argumentsSyntax);
		}

		return builder.toString();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
