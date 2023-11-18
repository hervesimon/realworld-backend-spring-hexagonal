package io.realworld.backend.domain.exception.helper;

public interface ExceptionSetMessage<T> {

    ExceptionCreator<T> setMessage(String message);
}
