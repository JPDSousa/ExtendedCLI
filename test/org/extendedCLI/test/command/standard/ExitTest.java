package org.extendedCLI.test.command.standard;

import org.extendedCLI.command.standard.Exit;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ExitTest {
    private Exit testExit = new Exit();

    @Test
    public void testDescription() {
        assertThat(testExit.getDescription(), containsString("system gracefully"));
    }

    @Test
    public void testUndo() {
        Exit spy = Mockito.spy(testExit);
        spy.undo();
        verify(spy, times(1)).undo();
    }
}
