package org.extendedCLI.command;

import org.extendedCLI.command.CLIBuilder;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCLI;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public class ExtendedCLITest {

	private String line = "command1 -p path_to_something";

	public void sensitiveBuilder_and_registerInsensitiveCommand_shouldThrows_NoSuchCommandException() {
		final AtomicBoolean noSuchCommandHandled = new AtomicBoolean(false);
		final Consumer<String> noSuchCommandHandler = line -> noSuchCommandHandled.set(true);

		// arrange
		final Command command1 =  mock(Command.class);
		final ExtendedCLI cli = new CLIBuilder(false)
				.withNoSuchCommandHandler(noSuchCommandHandler)
				.registerCommand("Command1", command1)
				.build();

		// act
		cli.execute(line);
		assertTrue(noSuchCommandHandled.get());
	}

	@Test
	public void registerCommand1_should_executeCommand1Only() {
		// arrange
		final Command command1 =  mock(Command.class);
		final Command command2 =  mock(Command.class);
		final ExtendedCLI cli = new CLIBuilder(true)
				.registerCommand("command1", command1)
				.registerCommand("command2", command2)
				.build();

		// act
		cli.execute(line);

		// assert
		verify(command1, times(1)).execute(" -p path_to_something");
		verify(command2, never()).execute(anyString());
	}

	@Test
	public void withoutCommand_shouldThrows_NoSuchCommandException() {
		// arrange
		final AtomicBoolean noCommandFound = new AtomicBoolean(false);
		final ExtendedCLI cli = new CLIBuilder(true)
				.withNoSuchCommandHandler(line -> noCommandFound.set(true))
				.build();

		// act
		cli.execute(line);
		assertTrue(noCommandFound.get());
	}
}
