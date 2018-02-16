package org.extendedCLI.ioAdapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import javax.annotation.Generated;

final class ReaderInputAdapter implements InputAdapter {
	
	private final BufferedReader input;
	
	ReaderInputAdapter(final Reader input) {
		if (input instanceof BufferedReader) {
			this.input = (BufferedReader) input;
		} else {
			this.input = new BufferedReader(input);
		}
	}
	
	@Override
	public String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), input);
	}

	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof ReaderInputAdapter) {
			if (!super.equals(object))
				return false;
			ReaderInputAdapter that = (ReaderInputAdapter) object;
			return Objects.equals(this.input, that.input);
		}
		return false;
	}
	
	
}
