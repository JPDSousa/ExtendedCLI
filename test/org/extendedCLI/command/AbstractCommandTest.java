package org.extendedCLI.command;

import org.extendedCLI.argument.Arguments;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AbstractCommandTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullInputAdapter() {
        new AbstractCommand(mock(Arguments.class)) {
            @Override
            public void undo() {

            }

            @Override
            protected void execute(ExtendedCommandLine commandLine) {

            }
        }.setInputAdapter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullOutputAdapter() {
        new AbstractCommand(mock(Arguments.class)) {
            @Override
            public void undo() {

            }

            @Override
            protected void execute(ExtendedCommandLine commandLine) {

            }
        }.setOutputAdapter(null);
    }
}