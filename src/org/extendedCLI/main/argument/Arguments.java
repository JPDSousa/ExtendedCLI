package org.extendedCLI.main.argument;

import java.util.stream.Stream;

import org.apache.commons.cli.Options;
import org.extendedCLI.main.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public interface Arguments {
	
	static final Argument HELP = Argument.create("h", Requires.FALSE, "Provides help about this command.");

	public static Arguments empty() {
		return new ArgumentsImpl();
	}

	public static Arguments create() {
		final ArgumentsImpl arguments = new ArgumentsImpl();
		arguments.addDefaultArgument(-1, HELP);

		return arguments;
	}

	Stream<Argument> stream();

	void addArgument(Argument arg);

	void addArgument(ArgumentEnum enumValue);

	void enableGroupOrder(int groupID);

	String getSyntax();

	void setRequirementRelation(Argument arg, Argument required);

	void setRequirementRelation(ArgumentEnum enumValue, ArgumentEnum required);

	ExtendedCommandLine validate(String line);

	void setGroupID(Argument arg, int groupID);

	void addArguments(ArgumentEnum[] enumValues);

	Options toOptions();
	
	Iterable<Argument> getArguments();
	
	
}
