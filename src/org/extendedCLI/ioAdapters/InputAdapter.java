package org.extendedCLI.ioAdapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("javadoc")
public interface InputAdapter {
	
	static InputAdapter fromBufferedReader(BufferedReader input) {
		return BufferedReaderAdapter.create(input);
	}
	
	static InputAdapter fromInputStreamReader(InputStreamReader reader) {
		return fromBufferedReader(new BufferedReader(reader));
	}
	
	static InputAdapter fromInputStream(InputStream stream) {
		return fromInputStreamReader(new InputStreamReader(stream));
	}
	
	public String readLine() throws IOException;

}
