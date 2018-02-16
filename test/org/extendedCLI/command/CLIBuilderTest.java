package org.extendedCLI.command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class CLIBuilderTest {
	
	private CLIBuilder cliBuilder;

	@Before
	public void setUp() {
		cliBuilder = new CLIBuilder(true);
	}

	@Test
	public void testDefaultCommandMap() {
		cliBuilder.build();
		assertTrue(cliBuilder.getCommands().get("HELP") instanceof Help);
		assertTrue(cliBuilder.getCommands().get("HISTORY") instanceof History);
		assertTrue(cliBuilder.getCommands().get("EXIT") instanceof Exit);
	}

	@Test
	public void testRegisterCommand() {
		final Command dummy = mock(Command.class);
		cliBuilder.registerCommand("DUMMY_COMMAND", dummy);
		cliBuilder.build();

		assertEquals(4, cliBuilder.getCommands().size());
		assertThat(cliBuilder.getCommands().get("DUMMY_COMMAND"), is(equalTo(dummy)));
	}

	@Test
	public void testSetInputAdapter() {
		final InputAdapter mockInputAdapter = mock(InputAdapter.class);
		final AbstractCommand dummyCommand = new History(Collections.emptyMap());

		cliBuilder.withInput(mockInputAdapter);
		cliBuilder.registerCommand("MOCK_COMMAND", dummyCommand);
		cliBuilder.build();

		assertThat(dummyCommand.getInput(), is(equalTo(mockInputAdapter)));
	}

	@Ignore
	@Test
	public void testSetBufferedReader() {
		final BufferedReader mockBufferedReader = mock(BufferedReader.class);
		final AbstractCommand mockCommand = new History(Collections.emptyMap());

		cliBuilder.withInput(mockBufferedReader);
		cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);
		cliBuilder.build();

		assertThat(mockCommand.getInput(), is(equalTo(InputAdapter.fromReader(mockBufferedReader))));
	}

	@Ignore
	@Test
	public void testSetInputStream() {
		final InputStream mockInputStream = mock(InputStream.class);
		final AbstractCommand mockCommand = new History(Collections.emptyMap());

		cliBuilder.withInput(mockInputStream);
		cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);
		cliBuilder.build();

		assertThat(mockCommand.getInput(), is(equalTo(InputAdapter.fromInputStream(mockInputStream))));
	}

	@Test
	public void testSetOutputAdapter() {
		final InputAdapter mockInputAdapter = mock(InputAdapter.class);
		final OutputAdapter mockOutputAdapter = mock(OutputAdapter.class);
		final AbstractCommand mockCommand = new History(Collections.emptyMap());

		cliBuilder.withInput(mockInputAdapter);
		cliBuilder.withOutput(mockOutputAdapter);
		cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);
		cliBuilder.build();

		assertThat(mockCommand.getOutput(), is(equalTo(mockOutputAdapter)));
	}

	@Ignore
	@Test
	public void testSetOutputStream() {
		final InputAdapter mockInputAdapter = mock(InputAdapter.class);
		final OutputStream mockOutputStream = mock(OutputStream.class);
		final AbstractCommand mockCommand = new History(Collections.emptyMap());

		cliBuilder.withInput(mockInputAdapter);
		cliBuilder.withOutput(mockOutputStream);
		cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);
		cliBuilder.build();

		assertThat(mockCommand.getOutput(), is(equalTo(OutputAdapter.fromOutputStream(mockOutputStream))));
	}
	
}
