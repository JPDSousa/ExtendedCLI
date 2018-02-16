package org.extendedCLI.command;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.cli.Options;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
public class AbstractCommandTest {

	private ByteArrayOutputStream stdOut;
	private AbstractCommand command;
	private OutputAdapter outputAdapter;

	private AtomicBoolean isExecuteCalled;
	private AtomicBoolean isUndoCalled;

	@Before
	public void setUp() {
		isExecuteCalled = new AtomicBoolean(false);
		isUndoCalled = new AtomicBoolean(false);
		
		stdOut = new ByteArrayOutputStream();
		outputAdapter = OutputAdapter.fromOutputStream(stdOut);
	}

	public void setUp(final Arguments args) {
		command = new AbstractCommand(args) {
			@Override
			public void undo() {
				isUndoCalled.set(true);
			}

			@Override
			protected void execute(ExtendedCommandLine commandLine) {
				isExecuteCalled.set(true);
			}
		};
		command.setIoSuppliers(() -> InputAdapter.SYSTEM_IN, () -> outputAdapter);
	}

	@Test
	public void testExecuteIsCalledWhenCommandGiven() {
		Arguments args = mock(Arguments.class);
		ExtendedCommandLine extendedCli = mock(ExtendedCommandLine.class);
		when(args.validate(anyString())).thenReturn(extendedCli);

		setUp(args);
		command.execute("test");

		assertTrue(isExecuteCalled.get());
	}

	@Test
	public void testHelpIsCalledWhenCommandIsNull() {
		final String syntax = "syntax";
		final String description = "description";

		final Arguments args = mock(Arguments.class);
		when(args.validate(anyString())).thenReturn(null);
		when(args.getSyntax()).thenReturn(syntax);
		when(args.toOptions()).thenReturn(new Options());

		setUp(args);
		command.setDescription(description);
		command.execute("test");

		final String output = stdOut.toString();
		assertThat(output, containsString(syntax));
		assertThat(output, containsString(description));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInputAdapter() {
		mock(AbstractCommand.class, Mockito.CALLS_REAL_METHODS).setIoSuppliers(null, null);;
	}

}
