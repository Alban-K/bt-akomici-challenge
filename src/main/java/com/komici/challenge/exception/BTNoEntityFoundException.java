package com.komici.challenge.exception;

public class BTNoEntityFoundException extends RuntimeException {


    /**
     * Default constructor.
     */
    public BTNoEntityFoundException() {
        super();
    }

    /**
     * Parametric constructor.
     *
     * @param message message
     * @param cause   cause of exception
     */
    public BTNoEntityFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Parametric constructor.
     *
     * @param message message
     */
    public BTNoEntityFoundException(final String message) {
        super(message);
    }

    /**
     * Parametric constructor.
     *
     * @param id of the entity
     */
    public BTNoEntityFoundException(final Long id) {
        super("No entity found with id=" + id);
    }



    /**
     * Parametric constructor.
     *
     * @param cause cause of exception
     */
    public BTNoEntityFoundException(final Throwable cause) {
        super(cause);
    }

}
