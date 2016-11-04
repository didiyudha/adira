package com.adira.exeption;

/**
 * Created by didi-realtime on 04/11/16.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
