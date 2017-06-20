package org.extendedCLI.argument;

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
import org.extendedCLI.command.ExtendedCommandLine;

class ArgumentsImpl implements Arguments{

	private final Set<Argument> args;
	private final Map<Integer, List<Argument>> groups;
	private final Map<Argument, List<Argument>> requirements;
	private final SortedSet<Integer> order;

	ArgumentsImpl() {
		args = new LinkedHashSet<>();
		groups = new LinkedHashMap<>();
		requirements = new LinkedHashMap<>();
		order = new TreeSet<>();
	}

	@Override
	public Stream<Argument> stream() {
		return args.stream();
	}

	@Override
	public void addArgument(Argument arg) {
		args.add(arg);
	}

	@Override
	public void addArgument(ArgumentEnum enumValue) {
		addArgument(enumValue.getArgument());
		setGroupID(enumValue.getArgument(), enumValue.getGroupID());
	}

	@Override
	public void enableGroupOrder(int groupID) {
		if (groups.values().contains(groupID)) {
			order.add(groupID);
		}
	}
	
	@Override
	public Iterable<Integer> getGroupOrder() {
		return order;
	}

	@Override
	public void addArguments(ArgumentEnum[] enumValues) {
		Arrays.stream(enumValues).forEach(a -> addArgument(a));
	}

	@Override
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

	@Override
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

	@Override
	public void setRequirementRelation(ArgumentEnum enumValue, ArgumentEnum required) {
		setRequirementRelation(enumValue.getArgument(), required.getArgument());
	}

	@Override
	public ExtendedCommandLine validate(String line) {
		final CommandLine commandLine;
		try {
			commandLine = new DefaultParser().parse(toOptions(), line.split(" "));
			if(commandLine.hasOption(HELP.getName())) {
				return null;
			}
			validateRequirements(commandLine);
			validateValues(commandLine);
			return new ExtendedCommandLine(commandLine);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
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

	private void validateRequirements(CommandLine commandLine) {
		for (Argument arg : requirements.keySet()) {
			thisRequiresThose(commandLine, arg, requirements.get(arg));
		}
	}

	@Override
	public Options toOptions() {
		final Options options = new Options();
		args.forEach(a -> options.addOption(a.toOption()));

		return options;
	}

	private static boolean and(CommandLine commandLine, List<Argument> l) {
		return !l.stream().filter(a -> !commandLine.hasOption(a.getName())).findFirst().isPresent();
	}

	private static void thisRequiresThose(CommandLine commandLine, Argument thiz, List<Argument> those) {
		if (commandLine.hasOption(thiz.getDescription()) && !and(commandLine, those)) {
			throw new IllegalArgumentException();
		}
	}
	
	void addDefaultArgument(int groupID, Argument argument) {
		addArgument(argument);
		groups.put(groupID, Arrays.asList(argument));
	}
}
