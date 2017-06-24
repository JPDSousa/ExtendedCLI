package org.extendedCLI.argument;

import java.util.Arrays;
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

	private final SetMultimap<Integer, Argument> groups;
	private final MutableGraph<Argument> args;
	private final SortedSet<Integer> order;

	ArgumentsImpl() {
		groups = LinkedHashMultimap.create();
		args = GraphBuilder.directed().build();
		order = new TreeSet<>();
	}

	@Override
	public Stream<Argument> stream() {
		return args.nodes().stream();
	}

	@Override
	public void addArgument(Argument arg) {
		checkArgument(arg != null, "Cannot add a null argument.");
		args.addNode(arg);
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
		if (contains(arg)) {
			groups.put(groupID, arg);
		}
	}

	@Override
	public void setRequirementRelation(Argument arg, Argument required) {
		if (contains(arg) && contains(required)) {
			args.putEdge(arg, required);
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
		return contains(arg) ? args.successors(arg) : null;
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
		order.stream()
		.map(groups::get)
		.map(Lists::newArrayList)
		.map(this::toStringArgumentList)
		.forEach(builder::append);
		
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
		boolean invalidArgs = stream()
				//filter all arguments that require a value
				.filter(a -> commandLine.hasOption(a.getName()) && a.requiresValue() == Requires.TRUE)
				//filter all arguments that have no value or an invalid one
				.filter(a -> commandLine.getOptionValue(a.getName(), "").isEmpty()
						|| !a.isValid(commandLine.getOptionValue(a.getName())))
				.findFirst().isPresent();

		if (invalidArgs) {
			throw new IllegalArgumentException();
		}

	}

	private void validateRequirements(CommandLine commandLine) {
		for (Argument arg : args.nodes()) {
			thisRequiresThose(commandLine, arg, args.successors(arg));
		}
	}

	@Override
	public Options toOptions() {
		final Options options = new Options();
		stream()
		.map(Argument::toOption)
		.forEach(options::addOption);

		return options;
	}

	private static boolean and(CommandLine commandLine, Set<Argument> l) {
		return !l.stream()
				.filter(a -> !commandLine.hasOption(a.getName()))
				.findFirst()
				.isPresent();
	}

	private static void thisRequiresThose(CommandLine commandLine, Argument thiz, Set<Argument> those) {
		if (commandLine.hasOption(thiz.getName()) && !and(commandLine, those)) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean contains(Argument argument) {
		return args.nodes().contains(argument);
	}
}
