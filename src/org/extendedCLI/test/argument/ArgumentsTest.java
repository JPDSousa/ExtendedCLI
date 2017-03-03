package org.extendedCLI.test.argument;

import static org.junit.Assert.*;

import org.extendedCLI.main.argument.Argument;
import org.extendedCLI.main.argument.Arguments;
import org.extendedCLI.main.argument.Requires;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ArgumentsTest {

	@Test
	public void testStaticEmpty() {
		final Arguments args = Arguments.empty();
		assertNotNull("Empty creator should return a non-null object", args);
		assertTrue("Empty arguments (object) should not have any arguments", args.stream().count() == 0);
	}

	@Test
	public void testStaticCreate() {
		final Arguments args = Arguments.create();
		assertNotNull("Creator should not return a null object", args);
	}

	@Test
	public void testStream() {
		final Arguments arguments = Arguments.create();
		assertNotNull(arguments.stream());
	}

	@Test
	public void testAddArgument() {
		final Arguments arguments = Arguments.empty();
		final Argument arg1 = Argument.create("x", Requires.FALSE);
		final Argument arg2 = Argument.create("y", Requires.FALSE);

		assertEquals("Initial (empty) arguments should have no arguments", 0, arguments.stream().count());
		arguments.addArgument(arg1);
		assertEquals("Empty arguments + 1 argument (should) = 1 argument", 1, arguments.stream().count());
		arguments.addArgument(arg2);
		assertEquals("Empty arguments + 2 arguments (should) = 2 arguments", 2, arguments.stream().count());
		arguments.addArgument(arg1);
		assertEquals("Adding an argument multiple time should have no effect on the number of arguments", 2,
				arguments.stream().count());
	}

	@Test
	public void testAddArgumentEnum() {
		final Arguments arguments = Arguments.empty();

		assertEquals("Initial (empty) arguments should have no arguments", 0, arguments.stream().count());
		arguments.addArgument(EnumTest.ARG1);
		assertEquals("Empty arguments + 1 argument (should) = 1 argument", 1, arguments.stream().count());
		arguments.addArgument(EnumTest.ARG2);
		assertEquals("Empty arguments + 2 arguments (should) = 2 arguments", 2, arguments.stream().count());
		arguments.addArgument(EnumTest.ARG2);
		assertEquals("Adding an argument multiple time should have no effect on the number of arguments", 2,
				arguments.stream().count());
	}
}
