package org.extendedCLI.command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;
import org.extendedCLI.ioAdapters.InputAdapter;
import org.extendedCLI.ioAdapters.OutputAdapter;
import org.junit.Before;
import org.junit.Test;

public class CLIBuilderTest {
  private CLIBuilder cliBuilder;

  @Before
  public void setUp() {
    cliBuilder = new CLIBuilder(true);
  }

  @Test
  public void testDefaultCommandMap() {
    assertEquals(3, cliBuilder.getCommands().size());
    assertTrue(cliBuilder.getCommands().get("HELP") instanceof Help);
    assertTrue(cliBuilder.getCommands().get("HISTORY") instanceof History);
    assertTrue(cliBuilder.getCommands().get("EXIT") instanceof Exit);
  }

  @Test
  public void testRegisterCommand() {
    cliBuilder.registerCommand("DUMMY_COMMAND", new AbstractCommand(Arguments.empty()) {
      @Override
      public void undo() {}

      @Override
      protected void execute(ExtendedCommandLine commandLine) {}
    });

    assertEquals(4, cliBuilder.getCommands().size());
    assertNotNull(cliBuilder.getCommands().get("DUMMY_COMMAND"));
  }

  @Test
  public void testSetInputAdapter() {
    InputAdapter mockInputAdapter = mock(InputAdapter.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputAdapter);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setInputAdapter(mockInputAdapter);
  }

  @Test
  public void testSetBufferedReader() {
    BufferedReader mockBufferedReader = mock(BufferedReader.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockBufferedReader);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setInputAdapter(any());
  }

  @Test
  public void testSetInputStream() {
    InputStream mockInputStream = mock(InputStream.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputStream);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setInputAdapter(any());
  }

  @Test
  public void testSetInputStreamReader() {
    InputStreamReader mockInputStreamReader = mock(InputStreamReader.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputStreamReader);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setInputAdapter(any());
  }

  @Test
  public void testSetOutputAdapter() {
    InputAdapter mockInputAdapter = mock(InputAdapter.class);
    OutputAdapter mockOutputAdapter = mock(OutputAdapter.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputAdapter);
    cliBuilder.setOutput(mockOutputAdapter);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setOutputAdapter(mockOutputAdapter);
  }

  @Test
  public void testSetOutputStream() {
    InputAdapter mockInputAdapter = mock(InputAdapter.class);
    OutputStream mockOutputStream = mock(OutputStream.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputAdapter);
    cliBuilder.setOutput(mockOutputStream);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setOutputAdapter(any());
  }

  @Test
  public void testSetOutputPrintStream() {
    InputAdapter mockInputAdapter = mock(InputAdapter.class);
    PrintStream mockOutputPrintStream = mock(PrintStream.class);
    AbstractCommand mockCommand = mock(AbstractCommand.class);

    cliBuilder.setInput(mockInputAdapter);
    cliBuilder.setOutput(mockOutputPrintStream);
    cliBuilder.registerCommand("MOCK_COMMAND", mockCommand);

    verify(mockCommand).setOutputAdapter(any());
  }
}
