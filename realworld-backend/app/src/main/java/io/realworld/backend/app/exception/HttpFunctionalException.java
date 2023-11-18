package io.realworld.backend.app.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

import io.realworld.backend.app.exception.dto.ExceptionResponse;
import io.realworld.backend.app.exception.helper.ExceptionSetStatut;
import io.realworld.backend.app.exception.helper.impl.HttpFunctionalExceptionBuilder;
import io.realworld.backend.domain.exception.FunctionalException;

/**
 * @author HMuludiki
 * Exception fonctionnelle
 */
public class HttpFunctionalException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String code;
    private final Map<String, Object> contextParams = new HashMap<>();

    public HttpFunctionalException(HttpStatus httpStatus, String code) {
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpFunctionalException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpFunctionalException(String code, String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpFunctionalException(String code, Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpFunctionalException(String code, String message, Map<String, Object> contextParams, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
        if (contextParams != null){
            this.contextParams.putAll(contextParams);
        }
    }

    public HttpFunctionalException(String code, String message, Map<String, Object> contextParams, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.code = code;
        this.httpStatus = httpStatus;
        if (contextParams != null) {
            this.contextParams.putAll(contextParams);
        }
    }

    public HttpFunctionalException(FunctionalException functionalException, final HttpStatus httpStatus) {
        super(functionalException.getMessage(),functionalException.getCause());
        this.code = functionalException.getCode();
        if(functionalException.getContextParams() != null) {
            this.contextParams.putAll(functionalException.getContextParams());
        }
        this.httpStatus = httpStatus;
    }

    public HttpFunctionalException(ExceptionResponse exceptionResponse, final HttpStatus httpStatus) {
        super(exceptionResponse.getMessage());
        this.code = exceptionResponse.getCode();
        if (exceptionResponse.getContextParams() != null) {
            this.contextParams.putAll(exceptionResponse.getContextParams());
        }
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public Map<String, Object> getContextParams() {
        return this.contextParams;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static ExceptionSetStatut<HttpFunctionalException> builder(){
        return new HttpFunctionalExceptionBuilder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HttpFunctionalException.class.getSimpleName() + "[", "]").add("httpStatus=" + httpStatus)
                                                                                              .add("code='" + code + "'")
                                                                                              .add("contextParams=" + contextParams)
                                                                                              .toString();
    }
}
