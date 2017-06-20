package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extendedCLI.argument.Arguments;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;

@SuppressWarnings("javadoc")
public class ArgumentsTest {

	private static Arguments args;
	
	@Before
	public void setUp() {
		args = Arguments.empty();
	}
	
	@Test
	public void testStaticEmpty() {
		assertNotNull(args);
		assertTrue(args.stream().count() == 0);
	}

	@Test
	public void testStaticCreate() {
		assertNotNull(Arguments.create());
	}

	@Test
	public void testStream() {
		assertNotNull(Arguments.create().stream());
	}

	@Test
	public void testAddArgument() {
		assertEquals(0, args.stream().count());
		args.addArgument(ArgsEnum.ARG1.getArgument());
		assertEquals(1, args.stream().count());
		args.addArgument(ArgsEnum.ARG2.getArgument());
		assertEquals(2, args.stream().count());
		args.addArgument(ArgsEnum.ARG3.getArgument());
		assertEquals(3, args.stream().count());
	}

	@Test
	public void testAddArgumentEnum() {
		assertEquals(0, args.stream().count());
		args.addArgument(ArgsEnum.ARG1);
		assertEquals(1, args.stream().count());
		args.addArgument(ArgsEnum.ARG2);
		assertEquals(2, args.stream().count());
		args.addArgument(ArgsEnum.ARG2);
		assertEquals(2,	args.stream().count());
		args.addArguments(ArgsEnum.values());
		assertEquals(ArgsEnum.values().length, args.stream().count());
	}

	@Test
	public void testGroupOrder() {
		final SortedSet<Integer> groups = new TreeSet<>();
		final ArgsEnum arg = ArgsEnum.ARG1;
		args.addArgument(arg);
		args.enableGroupOrder(arg.getGroupID());
		groups.add(arg.getGroupID());
		assertEquals(groups, args.getGroupOrder());
		args = Arguments.empty();
		args.enableGroupOrder(50);
		assertEquals(new TreeSet<>(), args.getGroupOrder());
	}
	
	@Test
	public void testSetGroupId() {
		final ArgsEnum arg = ArgsEnum.ARG1;
		final int newGroupId = 50;
		List<Integer> fromIt = new ArrayList<>();
		args.addArgument(arg);
		args.setGroupID(arg.getArgument(), newGroupId);
		args.enableGroupOrder(newGroupId);
		args.getGroupOrder().forEach(fromIt::add);
		assertTrue(fromIt.contains(newGroupId));
		
		final int newGroupIdFalse = 49;
		final ArgsEnum argFalse = ArgsEnum.ARG4;
		args.setGroupID(argFalse.getArgument(), newGroupIdFalse);
		args.enableGroupOrder(newGroupIdFalse);
		fromIt.clear();
		args.getGroupOrder().forEach(fromIt::add);
		assertFalse(fromIt.contains(newGroupIdFalse));
	}
	
	@Test
	public void testRequirements() {
		final ArgsEnum arg2 = ArgsEnum.ARG2;
		final ArgsEnum arg1 = ArgsEnum.ARG1;
		args.addArgument(arg1);
		args.addArgument(arg2);
		args.setRequirementRelation(arg1, arg2);
		assertTrue(Iterables.contains(args.getRequiredArguments(arg1), arg2.getArgument()));
	}
	
	@Test
	public void testRequirementsMissingArgs() {
		final ArgsEnum arg2 = ArgsEnum.ARG2;
		final ArgsEnum arg1 = ArgsEnum.ARG1;
		args.setRequirementRelation(arg1, arg2);
		assertNull(args.getRequiredArguments(arg1));
		args.addArgument(arg1);
		args.setRequirementRelation(arg1, arg2);
		assertTrue(Iterables.isEmpty(args.getRequiredArguments(arg1)));
	}

}
