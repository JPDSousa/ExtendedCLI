package org.extendedCLI.command.standard;

import org.extendedCLI.command.ExtendedCommandLine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.mockito.Mockito.mock;

import java.util.Collections;

@SuppressWarnings("javadoc")
public class ExitTest {

	private Exit exit;
	
	@Rule
	public final ExpectedSystemExit exitRule = ExpectedSystemExit.none();

	@Before
	public void setUp() {
		exit = new Exit(Collections.emptyList(), () -> System.exit(0));
	}

	@Test
	public void testExecuteIssuesExitSig() {
		exitRule.expectSystemExit();
		exit.execute(mock(ExtendedCommandLine.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUndoFails() {
		exit.undo();
	}

}
