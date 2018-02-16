package org.extendedCLI.command.standard;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class HelpTest {

	private Command help;
	private Map<String, Command> mockedCommands;
	private ByteArrayOutputStream stdOut;
	private OutputAdapter outputAdapter;


	@Before
	public void setUp() {
		stdOut = new ByteArrayOutputStream();
		mockedCommands = createMockedCommands(3);
		help = new Help(mockedCommands);
		outputAdapter = OutputAdapter.fromOutputStream(stdOut);
		help.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);
	}

	@Test
	public void testExecutePrintsHelpToStdOut() {
		help.execute("");
		
		final String actualOutput = stdOut.toString();
		
		for (Command cmd : mockedCommands.values()) {
			assertThat(actualOutput, containsString(cmd.getSyntax()));
			assertThat(actualOutput, containsString(cmd.getDescription()));
		}
	}

	private Map<String, Command> createMockedCommands(int numberOfCommands) {
		return IntStream.range(0, numberOfCommands)
				.boxed()
				.collect(Collectors.toMap(i -> i.toString(), i -> createDummyCommand("name"+i, "description"+i)));
	}

	@Test
	public void testDescription() {
		assertThat(help.getDescription(), containsString(Help.DESCRIPTION));
	}

	@Test
	public void testExecute() {
		final String name = "Coffee";
		final String description = "I make coffee";
		final Command coffeeCommand = createDummyCommand(name, description);
		
		coffeeCommand.setName(name);
		coffeeCommand.setDescription(description);

		help = new Help(Collections.singletonMap(name, coffeeCommand));
		help.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);
		help.execute("");
		
		assertThat(stdOut.toString(), containsString(description));
	}

	private Command createDummyCommand(final String name, final String description) {
		final AbstractCommand command = new AbstractCommand(Arguments.empty(), name, description) {
			
			@Override
			public void undo() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void execute(ExtendedCommandLine commandLine) {
				// TODO Auto-generated method stub
				
			}
		};
		command.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);
		return command;
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUndo() {
		help.undo();
	}
}
