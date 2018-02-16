package org.extendedCLI.argument;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.ArgumentEnum;
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddArgumentNull() {
		args.addArgument((Argument) null);
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddArgumentEnumNull() {
		args.addArgument((ArgumentEnum) null);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddArgumentsNull() {
		args.addArguments(null);
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRequirementRelationEnumNull() {
		args.setRequirementRelation((ArgumentEnum) null, (ArgumentEnum) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetRequirementsNull() {
		args.getRequiredArguments((Argument) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetRequirementsEnumNull() {
		args.getRequiredArguments((ArgumentEnum) null);
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
	
	@Test
	public void testGetSyntax() {
		args.addArgument(ArgsEnum.ARG1);
		args.addArgument(ArgsEnum.ARG2);
		args.addArgument(ArgsEnum.ARG3);
		args.addArgument(ArgsEnum.ARG4);
		args.enableGroupOrder(ArgsEnum.GROUP_ID_1);
		args.enableGroupOrder(ArgsEnum.GROUP_ID_2);
		assertNotNull(args.getSyntax());
	}
	
	@Test
	public void testToOption() {
		args.addArgument(ArgsEnum.ARG1);
		args.addArgument(ArgsEnum.ARG2);
		
		//Options has no equals implementation
		assertNotNull(args.toOptions());
	}

	@Test
	public void testValidate() {
		args = Arguments.create();
		args.addArgument(ArgsEnum.ARG1);
		args.addArgument(ArgsEnum.ARG2);
		args.addArgument(ArgsEnum.ARG3);
		args.addArgument(ArgsEnum.ARG4);
		//Help always returns a null command line
		assertNull(args.validate("-h"));
		assertNotNull(args.validate("-A -B -C this -D dArg"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidateUnrecognizedToken() {
		args.validate("-DontKnowThisToken");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidateInvalidArgValue() {
		args.addArgument(ArgsEnum.ARG3);
		args.validate("-C invalid");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidateFailRequirements() {
		args.addArgument(ArgsEnum.ARG1);
		args.addArgument(ArgsEnum.ARG2);
		args.setRequirementRelation(ArgsEnum.ARG1, ArgsEnum.ARG2);
		//Contains ARG1 but not ARG2, so it must fail
		args.validate("-A");
	}
}
