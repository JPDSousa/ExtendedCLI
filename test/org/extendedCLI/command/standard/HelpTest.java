package org.extendedCLI.command.standard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.cli.Options;
import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HelpTest {

  private Help help;
  private Map<String, Command> mockedCommands;
  private ByteArrayOutputStream stdOut;


  @Before
  public void setUp() {
    mockedCommands = createMockedCommands(3);
    help = new Help(mockedCommands);
    stdOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(stdOut));
  }

  @Test
  public void testExecutePrintsHelpToStdOut() {
    help.execute(mock(ExtendedCommandLine.class));
    assertEquals(stdOut.toString(), "usage: syntax0\n" +
        "description0\n" +
        "\n" +
        "usage: syntax1\n" +
        "description1\n" +
        "\n" +
        "usage: syntax2\n" +
        "description2\n\n");
  }

  @Test
  public void testExecuteIteratesThroughAllCommands() {
    help.execute(mock(ExtendedCommandLine.class));
    mockedCommands.values()
        .stream()
        .forEach(command -> verify(command).getSyntax());
  }

  private Map<String, Command> createMockedCommands(int numberOfCommands) {
    return IntStream.range(0, numberOfCommands).boxed().collect(Collectors.toMap(i -> i.toString(), i -> createdMockedCommand(i)));
  }

  private Command createdMockedCommand(int commandNum) {
    Command command = mock(Command.class);
    Arguments args = mock(Arguments.class);
    when(command.getSyntax()).thenReturn("syntax" + commandNum);
    when(command.getDescription()).thenReturn("description" + commandNum);
    when(command.getArgs()).thenReturn(args);
    when(args.toOptions()).thenReturn(new Options());
    return command;
  }
}
