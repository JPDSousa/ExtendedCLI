package org.extendedCLI.command;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;

@SuppressWarnings("javadoc")
public interface Command {
	
	void execute(String params);
	
	void undo();
	
	void setInputAdapter(InputAdapter input);
	void setOutputAdapter(OutputAdapter output);

	Arguments getArgs();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	String getSyntax();
	
}
