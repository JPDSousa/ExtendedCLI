package org.extendedCLI.command.standard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HistoryTest {

  private ByteArrayOutputStream stdOut;
  private String command1Name = "command1";
  private String command2Name = "command2";

  @Before
  public void setUp() {
    stdOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stdOut));
  }

  @Test
  public void testCommandsDontExecuteWhenHistoryIsEmpty() {
    History history = new History(new HashMap());
    history.execute(mock(ExtendedCommandLine.class));

    assertEquals("No commands yet.\n", stdOut.toString());
  }

  @Test
  public void testHistoryMapValuesAreIteratedWhenExecute() {
    Map<Long, Command> commands = createCommands();
    History history = new History(commands);

    history.execute(mock(ExtendedCommandLine.class));
    assertTrue(stdOut.toString().contains(createHistoryExecuteMessage(1L, command1Name)));
    assertTrue(stdOut.toString().contains(createHistoryExecuteMessage(2L, command2Name)));
  }

  @Test
  public void testUndo() {
    // no implementation
    new History(new HashMap()).undo();
  }

  private String createHistoryExecuteMessage(long date, String commandName) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()) + " -> " + commandName;
  }

  private Map<Long, Command> createCommands() {
    Map<Long, Command> commands = new HashMap();
    Command command1 = mock(Command.class);
    Command command2 = mock(Command.class);
    when(command1.toString()).thenReturn(command1Name);
    when(command2.toString()).thenReturn(command2Name);
    commands.put(1L, command1);
    commands.put(2L, command2);
    return commands;
  }
}
