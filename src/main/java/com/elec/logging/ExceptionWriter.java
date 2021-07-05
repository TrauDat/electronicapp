package com.elec.logging;

import java.io.PrintWriter;
import java.io.Writer;

/*
 * Handled writing exception to the Logger Tab and utility method needed
 * to facilitate logging of exceptions
 */
public class ExceptionWriter extends PrintWriter {

	public ExceptionWriter(Writer writer) {
		super(writer);
	}
	
	private String wrapAroundWithNewLine(String stringWithoutNewLines) {
		return ("\n" + stringWithoutNewLines + "\n");
	}
	
	/*
	 * Convert a stacktrace into a string
	 */
	public String getExceptionAsString(Throwable throwable) {
		throwable.printStackTrace(this);
		String exception = super.out.toString();
	
		return (wrapAroundWithNewLine(exception));
	}

}
