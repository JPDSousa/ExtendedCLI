package org.extendedCLI.argument;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
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

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import static com.google.common.base.Preconditions.*;

class ArgumentsImpl implements Arguments{

	private final Set<Argument> args;
	private final SetMultimap<Integer, Argument> groups;
	private final MutableGraph<Argument> requirements;
	private final SortedSet<Integer> order;

	ArgumentsImpl() {
		args = new LinkedHashSet<>();
		groups = LinkedHashMultimap.create();
		requirements = GraphBuilder.directed().build();
		order = new TreeSet<>();
	}

	@Override
	public Stream<Argument> stream() {
		return args.stream();
	}

	@Override
	public void addArgument(Argument arg) {
		checkArgument(arg != null, "Cannot add a null argument.");
		args.add(arg);
	}

	@Override
	public void addArgument(ArgumentEnum enumValue) {
		checkArgument(enumValue != null, "Cannot add a null argument.");
		addArgument(enumValue.getArgument());
		setGroupID(enumValue.getArgument(), enumValue.getGroupID());
	}

	@Override
	public void enableGroupOrder(int groupID) {
		if (groups.containsKey(groupID)) {
			order.add(groupID);
		}
	}
	
	@Override
	public Iterable<Integer> getGroupOrder() {
		return order;
	}

	@Override
	public void addArguments(ArgumentEnum[] enumValues) {
		checkArgument(enumValues != null, "Cannot add a null argument.");
		Arrays.stream(enumValues).forEach(this::addArgument);
	}

	@Override
	public void setGroupID(Argument arg, int groupID) {
		if (args.contains(arg)) {
			groups.put(groupID, arg);
		}
	}

	@Override
	public void setRequirementRelation(Argument arg, Argument required) {
		if (args.contains(arg) && args.contains(required)) {
			requirements.putEdge(arg, required);
		}
	}

	@Override
	public void setRequirementRelation(ArgumentEnum enumValue, ArgumentEnum required) {
		checkArgument(enumValue != null && required != null, "Cannot set a relation between null arguments.");
		setRequirementRelation(enumValue.getArgument(), required.getArgument());
	}

	@Override
	public Iterable<Argument> getRequiredArguments(Argument arg) {
		checkArgument(arg != null, "Cannot add a null argument.");
		return requirements.successors(arg);
	}

	@Override
	public Iterable<Argument> getRequiredArguments(ArgumentEnum arg) {
		checkArgument(arg != null, "Cannot add a null argument.");
		return getRequiredArguments(arg.getArgument());
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
		order.forEach(id -> builder.append(toStringArgumentList(Lists.newArrayList(groups.get(id)))));
		groups.keySet().stream()
		.filter(id -> !order.contains(id))
		.map(groups::get)
		.map(Lists::newArrayList)
		.map(this::toStringArgumentList)
		.forEach(builder::append);

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
		for (Argument arg : requirements.nodes()) {
			thisRequiresThose(commandLine, arg, requirements.successors(arg));
		}
	}

	@Override
	public Options toOptions() {
		final Options options = new Options();
		args.forEach(a -> options.addOption(a.toOption()));

		return options;
	}

	private static boolean and(CommandLine commandLine, Set<Argument> l) {
		return !l.stream().filter(a -> !commandLine.hasOption(a.getName())).findFirst().isPresent();
	}

	private static void thisRequiresThose(CommandLine commandLine, Argument thiz, Set<Argument> those) {
		if (commandLine.hasOption(thiz.getDescription()) && !and(commandLine, those)) {
			throw new IllegalArgumentException();
		}
	}
}
