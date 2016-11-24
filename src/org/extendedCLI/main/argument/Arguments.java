package org.extendedCLI.main.argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.extendedCLI.main.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class Arguments {
	
	private static final Argument HELP = Argument.create("h", Requires.FALSE, "Provides help about this command.");

	private final Set<Argument> args;
	private final Map<Integer, List<Argument>> groups;
	private final Map<Argument, List<Argument>> requirements;
	private final SortedSet<Integer> order;

	Arguments() {
		args = new LinkedHashSet<>();
		groups = new LinkedHashMap<>();
		requirements = new LinkedHashMap<>();
		order = new TreeSet<>();
	}

	public Stream<Argument> stream() {
		return args.stream();
	}

	public void addArgument(Argument arg) {
		args.add(arg);
	}

	public void addArgument(ArgumentEnum enumValue) {
		addArgument(enumValue.getArgument());
		setGroupID(enumValue.getArgument(), enumValue.getGroupID());
	}

	public void enableGroupOrder(int groupID) {
		if (groups.values().contains(groupID)) {
			order.add(groupID);
		}
	}

	public void addArguments(ArgumentEnum[] enumValues) {
		Arrays.stream(enumValues).forEach(a -> addArgument(a));
	}

	public void setGroupID(Argument arg, int groupID) {
		final List<Argument> args;

		if (this.args.contains(arg)) {
			if (groups.containsKey(groupID)) {
				args = groups.get(groupID);
			} else {
				args = new ArrayList<>();
				groups.put(groupID, args);
			}
			args.add(arg);
		}
	}

	public void setRequirementRelation(Argument arg, Argument required) {
		final List<Argument> requirements;
		if (args.contains(arg) && args.contains(required)) {
			if (this.requirements.containsKey(arg)) {
				requirements = this.requirements.get(arg);
			} else {
				requirements = new ArrayList<>();
				this.requirements.put(arg, requirements);
			}
			requirements.add(required);
		}
	}

	public void setRequirementRelation(ArgumentEnum enumValue, ArgumentEnum required) {
		setRequirementRelation(enumValue.getArgument(), required.getArgument());
	}

	public ExtendedCommandLine validate(String line) {
		final CommandLine commandLine;
		try {
			commandLine = new DefaultParser().parse(toOptions(), line.split(" "));
			if(commandLine.hasOption(HELP.getName())) {
				return null;
			}
			validateGroups(commandLine);
			validateRequirements(commandLine);
			validateValues(commandLine);
			return new ExtendedCommandLine(commandLine);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public String getSyntax() {
		final StringBuilder builder = new StringBuilder();
		order.forEach(id -> builder.append(toStringArgumentList(groups.get(id))));
		groups.keySet().stream()
		.filter(id -> !order.contains(id))
		.map(id -> groups.get(id))
		.forEach(group -> builder.append(toStringArgumentList(group)));

		return builder.toString();
	}

	private String toStringArgumentList(List<Argument> group) {
		final StringBuilder builder = new StringBuilder();
		
		if (group.size() == 1) {
			builder.append(" [").append(group.get(0).getFormattedName()).append("]");
		} else if (group.size() > 1) {
			builder.append(" ").append(group.stream().map(Argument::getFormattedName).collect(Collectors.joining("/")));
		}
		
		return builder.toString();
	}

	private void validateValues(CommandLine commandLine) {
		boolean invalidArgs = args.stream()
				.filter(a -> commandLine.hasOption(a.getName()) && a.requiresValue() == Requires.TRUE)
				.filter(a -> commandLine.getOptionValue(a.getName(), "").isEmpty()
						|| !a.isValid(commandLine.getOptionValue(a.getName())))
				.findFirst().isPresent();

		if (invalidArgs) {
			throw new IllegalArgumentException();
		}

	}

	private void validateGroups(CommandLine commandLine) {
		final long nonMutex = groups.keySet().stream()
				.map(id -> groups.get(id))
				.filter(l -> l.size() > 1)
				.filter(l -> !xor(commandLine, l)).count();

		if (nonMutex > 0) {
			throw new IllegalArgumentException();
		}
	}

	private void validateRequirements(CommandLine commandLine) {
		for (Argument arg : requirements.keySet()) {
			thisRequiresThose(commandLine, arg, requirements.get(arg));
		}
	}

	public Options toOptions() {
		final Options options = new Options();
		args.forEach(a -> options.addOption(a.toOption()));

		return options;
	}

	private static boolean xor(CommandLine commandLine, List<Argument> l) {
		return l.stream().filter(a -> commandLine.hasOption(a.getName())).count() == 1;
	}

	private static boolean and(CommandLine commandLine, List<Argument> l) {
		return !l.stream().filter(a -> !commandLine.hasOption(a.getName())).findFirst().isPresent();
	}

	private static void thisRequiresThose(CommandLine commandLine, Argument thiz, List<Argument> those) {
		if (commandLine.hasOption(thiz.getDescription()) && !and(commandLine, those)) {
			throw new IllegalArgumentException();
		}
	}

	public static Arguments empty() {
		return new Arguments();
	}

	public static Arguments create() {
		final Arguments arguments = new Arguments();
		arguments.addArgument(HELP);
		arguments.groups.put(-1, Arrays.asList(HELP));

		return arguments;
	}
}
