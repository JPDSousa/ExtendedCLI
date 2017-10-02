package org.extendedCLI.command;

import static org.junit.Assert.*;

import org.extendedCLI.command.standard.Exit;
import org.extendedCLI.command.standard.Help;
import org.extendedCLI.command.standard.History;
import org.junit.Test;

public class CLIBuilderTest {

  @Test
  public void testDefaultCommandMap() {
    CLIBuilder cliBuilder = new CLIBuilder(true);
    assertEquals(3, cliBuilder.getCommands().size());
    assertTrue(cliBuilder.getCommands().get("HELP") instanceof Help);
    assertTrue(cliBuilder.getCommands().get("HISTORY") instanceof History);
    assertTrue(cliBuilder.getCommands().get("EXIT") instanceof Exit);
  }
}