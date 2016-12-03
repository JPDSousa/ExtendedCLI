package org.extendedCLI.main.command;

import java.io.BufferedReader;

import org.extendedCLI.main.argument.Arguments;

@SuppressWarnings("javadoc")
public interface Command {
	
	void execute(String params);
	
	void undo();
	
	void setInputReader(BufferedReader inputreader);

	Arguments getArgs();

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	String getSyntax();
	
}
