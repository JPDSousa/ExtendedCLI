package org.extendedCLI.command.standard;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
public class HistoryTest {

	private static final String COMMAND_1 = "command1";
	private static final String COMMAND_2 = "command2";

	private OutputStream stdOut;
	private Command history;
	private OutputAdapter outputAdapter;

	@Before
	public void setUp() {
		stdOut = new ByteArrayOutputStream();
		outputAdapter = OutputAdapter.fromOutputStream(stdOut);
		history = new History(Collections.emptyMap());
		history.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);
	}

	@Test
	public void testEmptyHistory() {
		history.execute("bogusValue");
		assertThat(stdOut.toString(), containsString(History.NO_COMMANDS_STRING));
	}

	@Test
	public void testSomeHistory() {
		final long timeOffset = 888888L;
		final Command mockCommand =  mock(Command.class);

		history = new History(
				ImmutableMap.of(
						System.currentTimeMillis(), mockCommand,
						System.currentTimeMillis() + timeOffset, mockCommand)
				);
		
		history.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter); 
		history.execute("bogusValue");

		assertEquals(2, countMatches(stdOut.toString(), "->"));
	}

	@Test
	public void testHistoryMapValuesAreIteratedWhenExecute() {
		final History history = new History(createCommands());
		history.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);

		history.execute(mock(ExtendedCommandLine.class));
		assertThat(stdOut.toString(), containsString(COMMAND_1));
		assertThat(stdOut.toString(), containsString(COMMAND_2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUndo() {
		history.undo();
	}

	private Map<Long, Command> createCommands() {
		Command command1 = mock(Command.class);
		Command command2 = mock(Command.class);
		when(command1.toString()).thenReturn(COMMAND_1);
		when(command2.toString()).thenReturn(COMMAND_2);
		
		return ImmutableMap.of(
				1L, command1,
				2L, command2);
	}

	@Test
	public void testDescription() {
		assertThat(history.getDescription(), containsString(History.DESCRIPTION));
	}

}
