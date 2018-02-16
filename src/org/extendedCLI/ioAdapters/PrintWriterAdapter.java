package org.extendedCLI.ioAdapters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.annotation.Generated;

final class PrintWriterAdapter implements OutputAdapter {
	
	private final PrintWriter output;
	
	PrintWriterAdapter(PrintWriter output) {
		this.output = output;
	}


	@Override
	public void println(Object obj) {
		output.println(obj);
	}

	@Override
	public void print(Object obj) {
		output.print(obj);
	}

	@Override
	public PrintWriter asPrintWriter() {
		return output;
	}

	@Override
	public void flush() {
		output.flush();
	}
	
	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public int hashCode() {
		return Objects.hash(super.hashCode(), output);
	}
	
	@Override
	@Generated(value = "GuavaEclipsePlugin")
	public boolean equals(Object object) {
		if (object instanceof PrintWriterAdapter) {
			if (!super.equals(object))
				return false;
			PrintWriterAdapter that = (PrintWriterAdapter) object;
			return Objects.equals(this.output, that.output);
		}
		return false;
	}

	@Override
	public void close() throws IOException {
		output.close();
	}

}
