package com.example.topoftops.exception;

/**
 * Describes exception in Service
 *
 * @author Ilya Tsvetkov
 * @see Exception
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
