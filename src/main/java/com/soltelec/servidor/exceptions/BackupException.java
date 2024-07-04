package com.soltelec.servidor.exceptions;

public class BackupException extends RuntimeException {
    public BackupException() {
        super();
    }

    public BackupException(String message) {
        super(message);
    }

    public BackupException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackupException(Throwable cause) {
        super(cause);
    }
}
