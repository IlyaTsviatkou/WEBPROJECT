package com.example.topoftops.exception;

/**
 * Describes exception in ConnectionPool
 *
 * @author Ilya Tsvetkov
 * @see Exception
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
