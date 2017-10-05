package org.extendedCLI.ioAdapters;

import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PrintStreamAdapterTest {

  @Mock
  private PrintStream printStream;

  private PrintStreamAdapter printStreamAdapter;

  @Before
  public void setUp() {
    printStreamAdapter = PrintStreamAdapter.create(printStream);
  }

  @Test
  public void testPrintlnPrintsToStdOut() {
    String testString = "test";
    printStreamAdapter.println(testString);
    verify(printStream).println(testString);
  }
}
