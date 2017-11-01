package com.job.admin.framework.exception;

public class JobException extends Exception {
    private static final long serialVersionUID = 174841398690789156L;

    public JobException() {
    }

    public JobException(String msg) {
	super(msg);
    }

    public JobException(Throwable cause) {
	super(cause);
    }

    public JobException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public Throwable getUnderlyingException() {
	return super.getCause();
    }

    public String toString() {
	Throwable cause = getUnderlyingException();
	if ((cause == null) || (cause == this)) {
	    return super.toString();
	}
	return super.toString() + " [See nested exception: " + cause + "]";
    }
}
