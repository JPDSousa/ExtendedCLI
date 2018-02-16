package org.extendedCLI.ioAdapters;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;

@SuppressWarnings("javadoc")
public interface OutputAdapter extends Closeable {

	OutputAdapter SYSTEM_OUT = fromOutputStream(System.out);
	
	static OutputAdapter fromOutputStream(OutputStream output) {
		return new PrintWriterAdapter(new PrintWriter(output));
	}

	void println(Object obj);
	
	void print(Object obj);
	
	PrintWriter asPrintWriter();
	
	void flush();
	
}
