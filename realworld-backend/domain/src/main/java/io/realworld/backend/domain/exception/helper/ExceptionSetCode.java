package io.realworld.backend.domain.exception.helper;

public interface ExceptionSetCode<T> {
    /**
     * code de l'exception
     * @param code
     * @return
     */
    ExceptionSetMessage<T> setCode(String code);
}
