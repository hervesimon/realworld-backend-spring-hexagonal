package io.realworld.backend.app.exception.helper;

import org.springframework.http.HttpStatus;

public interface ExceptionSetStatut<T> {
    ExceptionSetBaseException<T> setHttpStatus(HttpStatus httpStatus);
}