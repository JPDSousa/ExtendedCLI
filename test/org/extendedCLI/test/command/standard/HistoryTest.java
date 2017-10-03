package org.extendedCLI.test.command.standard;

import org.extendedCLI.command.Command;
import org.extendedCLI.command.standard.History;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HistoryTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private Map<Long, Command> commandHistory;
    private History testHistory;

    @Before
    public void setup() {
        System.setOut(new PrintStream(outputStream));
        commandHistory = new LinkedHashMap<>();
        testHistory = new History(commandHistory);
    }

    @After
    public void cleanup() {
        System.setOut(null);
    }

    @Test
    public void testHistoryDescription() {
        assertThat(testHistory.getDescription(), containsString("history with timestamps"));
    }

    @Test
    public void testEmptyHistory() {
        testHistory.execute("bogusValue");
        assertThat(outputStream.toString(), containsString(History.NO_COMMANDS_STRING));
    }

    @Test
    public void testSomeHistory() {
        long timeOffset = 888888L;
        Command mockCommand =  mock(Command.class);

        commandHistory.put(System.currentTimeMillis(), mockCommand);
        commandHistory.put(System.currentTimeMillis() + timeOffset, mockCommand);

        testHistory = new History(commandHistory);
        testHistory.execute("bogusValue");

        assertEquals(2, countMatches(outputStream.toString(), "->"));
    }

    @Test
    public void testUndo() {
        History spy = Mockito.spy(testHistory);
        spy.undo();
        verify(spy, times(1)).undo();
    }
}
