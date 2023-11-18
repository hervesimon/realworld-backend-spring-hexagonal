package io.realworld.backend.app.exception.helper.impl;

import io.realworld.backend.app.exception.HttpFunctionalException;
import io.realworld.backend.app.exception.helper.ExceptionFinalBuilder;
import io.realworld.backend.app.exception.helper.ExceptionSetBaseException;
import io.realworld.backend.app.exception.helper.ExceptionSetStatut;
import io.realworld.backend.domain.exception.FunctionalException;
import io.realworld.backend.domain.exception.TechnicalException;
import io.realworld.backend.domain.exception.helper.impl.ExceptionBuilder;

import org.springframework.http.HttpStatus;

public class HttpFunctionalExceptionBuilder extends ExceptionBuilder<HttpFunctionalException> implements ExceptionSetStatut<HttpFunctionalException>,
        ExceptionSetBaseException<HttpFunctionalException>, ExceptionFinalBuilder<HttpFunctionalException> {

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    @Override
    public HttpFunctionalException build() {
        return new HttpFunctionalException(this.getCode(),this.getMessage(),this.getContextParams(),this.getThrowable(),this.getHttpStatus());
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public ExceptionSetBaseException<HttpFunctionalException> setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    @Override
    public ExceptionFinalBuilder<HttpFunctionalException> setFunctionalException(FunctionalException exp) {
        this.setCode(exp.getCode());
        this.setMessage(exp.getMessage());
        if (exp.getContextParams() != null) {
            this.getContextParams().putAll(exp.getContextParams());
        }
        this.addThrowable(exp);
        return this;
    }



    @Override
    public ExceptionFinalBuilder<HttpFunctionalException> setTechnicalException(TechnicalException exp) {
        this.setCode(exp.getCode());
        this.setMessage(exp.getMessage());
        if (exp.getContextParams() != null) {
            this.getContextParams().putAll(exp.getContextParams());
        }
        this.addThrowable(exp);
        return this;
    }
}
