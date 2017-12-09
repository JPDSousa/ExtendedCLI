package org.extendedCLI.test.command.standard;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.extendedCLI.command.standard.Help;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HelpTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private Map<String, Command> commands = new LinkedHashMap<>();
    private Help testHelp = new Help(commands);

    @Before
    public void setup() {
        System.setOut(new PrintStream(outputStream));
        commands = new LinkedHashMap<>();
        testHelp = new Help(commands);
    }

    @After
    public void cleanup() {
        System.setOut(null);
    }

    @Test
    public void testDescription() {
        assertThat(testHelp.getDescription(), containsString("command list documentation"));
    }

    @Test
    public void testExecute() {
        Command coffeeCommand = new AbstractCommand(Arguments.empty()) {
            @Override
            public void undo() {

            }

            @Override
            protected void execute(ExtendedCommandLine commandLine) {

            }
        };
        coffeeCommand.setDescription("I make coffee");

        commands.put("COFFEE", coffeeCommand);
        testHelp = new Help(commands);
        testHelp.execute("bogusValue");

        assertThat(outputStream.toString(), containsString("I make coffee"));
    }

    @Test
    public void testUndo() {
        Help spy = Mockito.spy(testHelp);
        spy.undo();
        verify(spy, times(1)).undo();
    }
}
