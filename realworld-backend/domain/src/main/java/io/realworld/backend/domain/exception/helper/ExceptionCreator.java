package io.realworld.backend.domain.exception.helper;

public interface ExceptionCreator<T> {

    ExceptionCreator<T> addContextParam(String key, Object value);

    ExceptionCreator<T> addThrowable(Throwable throwable);

    T build();
}
