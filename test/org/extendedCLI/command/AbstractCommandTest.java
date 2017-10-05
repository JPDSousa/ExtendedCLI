package org.extendedCLI.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.Options;
import org.extendedCLI.argument.Arguments;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractCommandTest {

  private ByteArrayOutputStream stdOut;

  @Before
  public void setUp() {
    stdOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stdOut));
  }

  @Test
  public void testExecuteIsCalledWhenCommandGiven() {
    Arguments args = mock(Arguments.class);
    ExtendedCommandLine extendedCli = mock(ExtendedCommandLine.class);
    when(args.validate(anyString())).thenReturn(extendedCli);

    AbstractCommand abstractCommand = createAbstractCommand(args);
    abstractCommand.execute("test");

    assertEquals("Execute called", stdOut.toString());
  }

  @Test
  public void testHelpIsCalledWhenCommandIsNull() {
    Arguments args = mock(Arguments.class);
    when(args.validate(anyString())).thenReturn(null);
    when(args.getSyntax()).thenReturn("syntax");
    when(args.toOptions()).thenReturn(new Options());

    AbstractCommand abstractCommand = createAbstractCommand(args);
    abstractCommand.setDescription("description");
    abstractCommand.execute("test");

    assertEquals("usage:  syntax\n" +
        "description\n" +
        "\n", stdOut.toString());
  }

  private AbstractCommand createAbstractCommand(Arguments args) {
    return new AbstractCommand(args) {
      @Override
      public void undo() {
        System.out.print("Undo called");
      }

      @Override
      protected void execute(ExtendedCommandLine commandLine) {
        System.out.print("Execute called");
      }
    };
  }
}
