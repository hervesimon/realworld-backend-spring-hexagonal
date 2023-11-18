package io.realworld.backend.app.exception.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

import io.realworld.backend.domain.exception.FunctionalException;

/**
 * @author hmuludiki
 * La structure de la réponse HTTP quand une exception est levée
 */
public class ExceptionResponse {

    /**
     * paramètre de contexte
     */
    private final Map<String, Object> contextParams = new HashMap<>();
    /**
     * code du message
     */
    private String code;

    /**
     * contenu du message
     */
    private String message;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;

    }

    public ExceptionResponse(String code, String message, Map<String, Object> contextParams) {
        this.code = code;
        this.message = message;
        if (contextParams != null && !contextParams.isEmpty()) {
            this.contextParams.putAll(contextParams);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getContextParams() {
        return this.contextParams;
    }

    public static final ExceptionResponse fromFunctionException(FunctionalException fexp) {
        return new ExceptionResponse(fexp.getCode(), fexp.getMessage(), fexp.getContextParams());
    }

    public enum CODES {
        UNEXPECTED("error.unexpected", "erreur inattendue", HttpStatus.INTERNAL_SERVER_ERROR),
        GLOBAL_EXCEPTION("error.globalexception", "erreur interne inattendue", HttpStatus.INTERNAL_SERVER_ERROR),
        METHOD_NOT_SUPPORTED("error.method_not_supported", "méthode non supportée", HttpStatus.METHOD_NOT_ALLOWED),
        NO_HANDLE_FOUND("error.no_handle_found", "handle non trouvé", HttpStatus.NOT_FOUND);

        /**
         * Code identifiant le message
         */
        private final String code;

        /**
         * contenu du message
         */
        private final String message;

        /**
         * statut http
         */
        private final HttpStatus httpStatus;

        CODES(String code, String defaultMessage, HttpStatus httpStatus) {
            this.code = code;
            this.message = defaultMessage;
            this.httpStatus = httpStatus;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

    }

}
