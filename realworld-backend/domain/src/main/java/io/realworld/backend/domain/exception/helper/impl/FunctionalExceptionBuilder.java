package io.realworld.backend.domain.exception.helper.impl;

import io.realworld.backend.domain.exception.FunctionalException;

/**
 * Builder pour les exceptions fonctionnelle
 */
public class FunctionalExceptionBuilder extends ExceptionBuilder<FunctionalException> {

    @Override
    public FunctionalException build() {
        return new FunctionalException(this.getCode(),this.getMessage(),this.getContextParams(),this.getThrowable());
    }
}
