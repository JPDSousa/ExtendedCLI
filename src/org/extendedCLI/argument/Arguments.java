package org.extendedCLI.argument;

import java.util.stream.Stream;

import org.apache.commons.cli.Options;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public interface Arguments {
	
	static final Argument HELP = Argument.create("h", Requires.FALSE, "Provides help about this command.");

	public static Arguments empty() {
		return new ArgumentsImpl();
	}

	public static Arguments create() {
		final ArgumentsImpl arguments = new ArgumentsImpl();
		arguments.addArgument(HELP);
		arguments.setGroupID(HELP, -1);

		return arguments;
	}

	Stream<Argument> stream();

	void addArgument(Argument arg);
	void addArgument(ArgumentEnum enumValue);
	void addArguments(ArgumentEnum[] enumValues);

	void enableGroupOrder(int groupID);
	Iterable<Integer> getGroupOrder();

	String getSyntax();

	void setRequirementRelation(Argument arg, Argument required);
	void setRequirementRelation(ArgumentEnum enumValue, ArgumentEnum required);
	Iterable<Argument> getRequiredArguments(Argument root);
	Iterable<Argument> getRequiredArguments(ArgumentEnum root);

	ExtendedCommandLine validate(String line);

	void setGroupID(Argument arg, int groupID);

	Options toOptions();
	
	boolean contains(Argument argument);
	
	
}
