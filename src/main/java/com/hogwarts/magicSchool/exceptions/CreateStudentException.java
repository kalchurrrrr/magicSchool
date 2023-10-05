package com.hogwarts.magicSchool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateStudentException extends RuntimeException {
    public CreateStudentException() {
    }

    public CreateStudentException(String s) {
    }

    public CreateStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateStudentException(Throwable cause) {
        super(cause);
    }

    public CreateStudentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

