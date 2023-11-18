package io.realworld.backend.domain.exception.helper.impl;

import java.util.HashMap;
import java.util.Map;

import io.realworld.backend.domain.exception.helper.ExceptionCreator;
import io.realworld.backend.domain.exception.helper.ExceptionSetCode;
import io.realworld.backend.domain.exception.helper.ExceptionSetMessage;

/**
 * Classe abstraire des builders d'exceptions de base
 * @param <T>
 */
public abstract class ExceptionBuilder<T> implements ExceptionSetCode<T>, ExceptionSetMessage<T>, ExceptionCreator<T> {

    private String code;

    private String message;

    private final Map<String,Object> contextParams = new HashMap<>();

    private Throwable throwable;

    @Override
    public ExceptionCreator<T> addContextParam(String key, Object value) {
        this.contextParams.put(key,value);
        return this;
    }

    @Override
    public ExceptionCreator<T> addThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override
    public ExceptionSetMessage<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public ExceptionCreator<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getContextParams() {
        return contextParams;
    }

    public Throwable getThrowable() {
        return throwable;
    }

}
