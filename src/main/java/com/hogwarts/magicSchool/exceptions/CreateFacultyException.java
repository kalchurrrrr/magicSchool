package com.hogwarts.magicSchool.exceptions;


public class CreateFacultyException extends RuntimeException {
    public CreateFacultyException() {
    }

    public CreateFacultyException(String message) {
        super(message);
    }

    public CreateFacultyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateFacultyException(Throwable cause) {
        super(cause);
    }

    public CreateFacultyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

