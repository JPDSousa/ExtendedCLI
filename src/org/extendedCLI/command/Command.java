package org.extendedCLI.command;

import java.util.function.Supplier;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

@SuppressWarnings("javadoc")
public interface Command {
	
	void execute(String params);
	
	void undo();
	
	Arguments getArgs();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	String getSyntax();
	
	void help();

	void setIoSuppliers(Supplier<InputAdapter> inputSupplier, Supplier<OutputAdapter> outputSupplier);
	
}
