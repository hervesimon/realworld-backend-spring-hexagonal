package io.realworld.backend.domain.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import io.realworld.backend.domain.exception.helper.impl.FunctionalExceptionBuilder;

/**
 * Exception fonctionnelle. Utilisable pour
 * remonter un manque de cohérence de donnnee
 * lors des étapes de validation
 * dans le domaine
 */
public class FunctionalException extends Exception implements BaseException {

    /**
     * code de l'exception
     */
    private final String code;

    /**
     * paramètre de contexte
     */
    private final Map<String, Object> contextParams = new HashMap<>();

    public FunctionalException(String code) {
        super();
        this.code = code;
    }

    public FunctionalException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public FunctionalException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public FunctionalException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public FunctionalException(String code, String message, Map<String, Object> contextParams) {
        super(message);
        this.code = code;
        this.contextParams.putAll(contextParams);
    }

    public FunctionalException(String code, String message, Map<String, Object> contextParams, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.contextParams.putAll(contextParams);
    }

    /**
     * Code identifiant le contenu de l'exception
     *
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Liste des parametres de context
     *
     * @return
     */
    public Map<String, Object> getContextParams() {
        return contextParams;
    }

    public static FunctionalExceptionBuilder builder() {
        return new FunctionalExceptionBuilder();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FunctionalException.class.getSimpleName() + "[", "]").add("code='" + code + "'")
                                                                                           .add("message='" + this.getMessage() + "'")
                                                                                           .add("cause='" + this.getCause() + "'")
                                                                                           .add("contextParams=[" + this.getContextParams().toString() + "]")
                                                                                           .toString();
    }
}
