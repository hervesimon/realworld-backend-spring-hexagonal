package io.realworld.backend.domain.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import io.realworld.backend.domain.exception.helper.impl.TechnicalExceptionBuilder;

/**
 * Exception technique. Utilisable pour remonter:
 * les erreurs techniques non récupérable : ex repertoire inexistant
 */
public class TechnicalException extends RuntimeException implements BaseException {

    /**
     * code de l'exception
     */
    private final String code;

    /**
     * paramètre de contexte
     */
    private final Map<String, Object> contextParams = new HashMap<>();

    public TechnicalException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public TechnicalException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public TechnicalException(String code) {
        this.code = code;
    }

    public TechnicalException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public TechnicalException(String code, String message, Map<String, Object> contextParams) {
        super(message);
        this.code = code;
        this.contextParams.putAll(contextParams);
    }

    public TechnicalException(String code, String message, Map<String, Object> contextParams, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.contextParams.putAll(contextParams);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getContextParams() {
        return contextParams;
    }

    public static TechnicalExceptionBuilder builder() {
        return new TechnicalExceptionBuilder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TechnicalException.class.getSimpleName() + "[", "]").add("code='" + code + "'")
                                                                                          .add("message='" + this.getMessage() + "'")
                                                                                          .add("cause='" + this.getCause() + "'")
                                                                                          .add("contextParams=[" + this.getContextParams().toString() + "]")
                                                                                          .toString();
    }

}