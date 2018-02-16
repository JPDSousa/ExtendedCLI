package org.extendedCLI.ioAdapters;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("javadoc")
public class PrintWriterAdapterTest {

	private OutputStream output;
	private PrintWriterAdapter printStreamAdapter;

	@Before
	public void setUp() {
		output = new ByteArrayOutputStream();
		printStreamAdapter = new PrintWriterAdapter(new PrintWriter(output));
	}

	@Test
	public void testPrintlnPrintsToStdOut() {
		final String expected = "test";
		
		printStreamAdapter.println(expected);
		printStreamAdapter.flush();
		assertEquals(expected + "\r\n", output.toString());
	}
	
	@Test
	public final void testPrintPrintsToStdOut() {
		final String expected = "test";
		
		printStreamAdapter.print(expected);
		printStreamAdapter.flush();
		assertEquals(expected, output.toString());
	}
}
