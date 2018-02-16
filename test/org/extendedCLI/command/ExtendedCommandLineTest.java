package org.extendedCLI.command;

import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.extendedCLI.argument.Argument;
import org.extendedCLI.argument.ArgumentEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class ExtendedCommandLineTest {

	@Mock
	private CommandLine commandLine;
	private ExtendedCommandLine extendedCommandLine;

	@Before
	public void setUp() {
		extendedCommandLine = new ExtendedCommandLine(commandLine);
	}

	@Test
	public void testHasArgIsTrueGivenArgument() {
		Argument argument = createArgument("name", Optional.empty());
		when(commandLine.hasOption("name")).thenReturn(true);

		assertTrue(extendedCommandLine.hasArg(argument));
	}

	@Test
	public void testHasArgIsFalseGivenArgument() {
		Argument argument = createArgument("doesn't exist", Optional.empty());
		when(commandLine.hasOption("doesn't exist")).thenReturn(false);

		assertFalse(extendedCommandLine.hasArg(argument));
	}

	@Test
	public void testHasArgIsTrueGivenArgumentEnum() {
		ArgumentEnum argumentEnum = mock(ArgumentEnum.class);
		Argument argument = createArgument("name", Optional.empty());
		when(argumentEnum.getArgument()).thenReturn(argument);
		when(commandLine.hasOption("name")).thenReturn(true);

		assertTrue(extendedCommandLine.hasArg(argumentEnum));
	}

	@Test
	public void testHasArgIsFalseGivenArgumentEnum() {
		ArgumentEnum argumentEnum = mock(ArgumentEnum.class);
		Argument argument = createArgument("doesn't exist", Optional.empty());
		when(argumentEnum.getArgument()).thenReturn(argument);
		when(commandLine.hasOption("doesn't exist")).thenReturn(false);

		assertFalse(extendedCommandLine.hasArg(argumentEnum));
	}

	@Test
	public void testGetValueGivenArgument() {
		Argument arg = createArgument("name", Optional.of("default value"));
		when(commandLine.getOptionValue("name", "default value")).thenReturn("option value");

		String retVal = extendedCommandLine.getValue(arg);
		assertEquals("option value", retVal);
	}

	@Test
	public void testGetValueGivenArgumentEnum() {
		Argument arg = createArgument("name", Optional.of("default value"));
		ArgumentEnum argumentEnum = mock(ArgumentEnum.class);
		when(argumentEnum.getArgument()).thenReturn(arg);
		when(commandLine.getOptionValue("name", "default value")).thenReturn("option value");

		String retVal = extendedCommandLine.getValue(argumentEnum);
		assertEquals("option value", retVal);
	}

	private Argument createArgument(String name, Optional<String> defaultValue) {
		Argument argument = mock(Argument.class);
		when(argument.getName()).thenReturn(name);
		defaultValue.ifPresent(value -> when(argument.getDefaultValue()).thenReturn(value));
		return argument;
	}
}
