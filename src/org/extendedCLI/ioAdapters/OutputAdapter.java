package org.extendedCLI.ioAdapters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

@SuppressWarnings("javadoc")
public interface OutputAdapter {

	static OutputAdapter fromPrintStream(PrintStream output) {
		return PrintStreamAdapter.create(output);
	}

	static OutputAdapter fromOutputStream(OutputStream output) {
		return fromPrintStream(new PrintStream(output));
	}

	public void println(String str) throws IOException;
}
