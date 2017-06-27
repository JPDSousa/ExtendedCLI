package org.extendedCLI.ioAdapters;

import java.io.PrintStream;

final class PrintStreamAdapter implements OutputAdapter {

	static PrintStreamAdapter create(PrintStream output) {
		return new PrintStreamAdapter(output);
	}
	
	private final PrintStream output;
	
	private PrintStreamAdapter(PrintStream output) {
		this.output = output;
	}

	@Override
	public void println(String str) {
		output.println(str);
	}

}
