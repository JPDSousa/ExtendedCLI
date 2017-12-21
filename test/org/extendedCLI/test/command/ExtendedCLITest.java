package org.extendedCLI.test.command;

import org.extendedCLI.command.CLIBuilder;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCLI;
import org.extendedCLI.exceptions.NoSuchCommandException;
import org.junit.Test;

import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class ExtendedCLITest {

    private String line = "command1 -p path_to_something";

    private CLIBuilder createInsensitiveCLIBuilder() {
        return new CLIBuilder(true);
    }

    private CLIBuilder createSensitiveCLIBuilder() {
        return new CLIBuilder(false);
    }

    @Test(expected = NoSuchCommandException.class)
    public void sensitiveBuilder_and_registerInsensitiveCommand_shouldThrows_NoSuchCommandException() throws Exception {

        // arrange
        Command command1 =  mock(Command.class);
        CLIBuilder builder = createSensitiveCLIBuilder();

        builder.registerCommand("Command1", command1);
        ExtendedCLI cli = builder.build();

        // act
        cli.execute(line);
    }

    @Test
    public void registerCommand1_should_executeCommand1Only() throws Exception {

        // arrange
        Command command1 =  mock(Command.class);
        Command command2 =  mock(Command.class);
        CLIBuilder builder = createInsensitiveCLIBuilder();
        builder.registerCommand("command1", command1);
        builder.registerCommand("command2", command2);
        ExtendedCLI cli = builder.build();

        // act
        cli.execute(line);

        // assert
        verify(command1, times(1)).execute(" -p path_to_something");
        verify(command2, never()).execute(anyString());
    }

    @Test(expected = NoSuchCommandException.class)
    public void withoutCommand_shouldThrows_NoSuchCommandException() throws Exception {

        // arrange
        CLIBuilder builder = createInsensitiveCLIBuilder();
        ExtendedCLI cli = builder.build();

        // act
        cli.execute(line);
    }
}
