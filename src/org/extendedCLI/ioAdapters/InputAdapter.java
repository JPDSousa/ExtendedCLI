package org.extendedCLI.ioAdapters;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@SuppressWarnings("javadoc")
public interface InputAdapter {
	
	InputAdapter SYSTEM_IN = fromInputStream(System.in);
	
	static InputAdapter fromReader(Reader input) {
		return new ReaderInputAdapter(input);
	}
	
	static InputAdapter fromInputStream(InputStream stream) {
		return fromReader(new InputStreamReader(stream));
	}
	
	public String readLine();

}
