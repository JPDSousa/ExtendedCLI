package org.extendedCLI.ioAdapters;

import java.io.BufferedReader;
import java.io.IOException;

final class BufferedReaderAdapter implements InputAdapter {

	static BufferedReaderAdapter create(BufferedReader input) {
		return new BufferedReaderAdapter(input);
	}
	
	private final BufferedReader input;
	
	private BufferedReaderAdapter(BufferedReader input) {
		this.input = input;
	}
	
	@Override
	public String readLine() throws IOException {
		return input.readLine();
	}
}
