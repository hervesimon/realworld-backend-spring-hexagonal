package io.realworld.backend.app.exception.helper;

import io.realworld.backend.domain.exception.FunctionalException;
import io.realworld.backend.domain.exception.TechnicalException;
import io.realworld.backend.domain.exception.helper.ExceptionSetCode;

public interface ExceptionSetBaseException<T> extends ExceptionSetCode<T> {

    ExceptionFinalBuilder<T> setFunctionalException(FunctionalException exp);

    ExceptionFinalBuilder<T> setTechnicalException(TechnicalException exp);
}
