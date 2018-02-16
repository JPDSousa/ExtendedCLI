package org.extendedCLI.ioAdapters;

import java.io.BufferedReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class ReaderAdapterTest {

	@Mock
	private BufferedReader reader;
	private ReaderInputAdapter bufferedReaderAdapter;

	@Before
	public void setUp() {
		bufferedReaderAdapter = new ReaderInputAdapter(reader);
	}

	@Test
	public void testReadLineReturnsStringInBufferedReader() throws IOException {
		String readString = "line read";
		when(reader.readLine()).thenReturn(readString);

		String retVal = bufferedReaderAdapter.readLine();
		assertEquals(readString, retVal);
	}
	
}
