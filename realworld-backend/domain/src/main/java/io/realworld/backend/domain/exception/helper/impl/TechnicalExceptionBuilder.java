package io.realworld.backend.domain.exception.helper.impl;

import io.realworld.backend.domain.exception.TechnicalException;

public class TechnicalExceptionBuilder extends ExceptionBuilder<TechnicalException> {

    @Override
    public TechnicalException build() {
        return new TechnicalException(this.getCode(),this.getMessage(),this.getContextParams(),this.getThrowable());
    }
}
