package com.komici.challenge.exception;

public class BTOperationNotAllowedException extends RuntimeException {


    /**
     * Default constructor.
     */
    public BTOperationNotAllowedException() {
        super();
    }

    /**
     * Parametric constructor.
     *
     * @param message message
     * @param cause   cause of exception
     */
    public BTOperationNotAllowedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Parametric constructor.
     *
     * @param message message
     */
    public BTOperationNotAllowedException(final String message) {
        super(message);
    }


    /**
     * Parametric constructor.
     *
     * @param cause cause of exception
     */
    public BTOperationNotAllowedException(final Throwable cause) {
        super(cause);
    }

}
