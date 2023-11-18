package io.realworld.backend.app.exception.config;

import io.realworld.backend.app.exception.ExceptionMessageAccessor;
import io.realworld.backend.app.exception.HttpFunctionalException;
import io.realworld.backend.app.exception.dto.ExceptionResponse;
import io.realworld.backend.domain.exception.FunctionalException;
import io.realworld.backend.domain.exception.TechnicalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author hmuludiki
 * Classe abstraite qui gère les exceptions
 * Cette classe doit être étendue pour être utilisée avec la directive "@ControllerAdvice"
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger _LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    protected static HttpHeaders httpHeader;

    private static final String DELIMITER = ",";

    static {
        httpHeader = new HttpHeaders();
        httpHeader.add("content-type", "application/json;charset=UTF-8");
    }

    private final ExceptionMessageAccessor exceptionMessageHelper;

    public ExceptionHandler(final MessageSource messageSource) {
        this.exceptionMessageHelper = new ExceptionMessageAccessor(new MessageSourceAccessor(messageSource));
    }

    private ExceptionResponse buildResponse(String code, String message, Map<String, Object> context) {
        String currentMessage = this.exceptionMessageHelper.getMessage(code, message, context);
        return new ExceptionResponse(code, currentMessage, context);
    }

    private ExceptionResponse buildResponse(String code, String message) {
        String currentMessage = this.exceptionMessageHelper.getMessage(code, message, null);
        return new ExceptionResponse(code, currentMessage, null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request) {
        // Toutes les erreurs internes du serveur sont tracées
        if (_LOGGER.isErrorEnabled()) {
            _LOGGER.error("Une erreur technique interne non gérée s'est produite exception", e);
        }
        return new ResponseEntity<>(this.buildResponse(ExceptionResponse.CODES.UNEXPECTED.getCode(), ExceptionResponse.CODES.UNEXPECTED.getMessage()),
                ExceptionResponse.CODES.UNEXPECTED.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
        // Toutes les erreurs internes du serveur sont tracées
        if (_LOGGER.isWarnEnabled()) {
            _LOGGER.warn("Une erreur exception non runtime interne non gérée s'est produite exception", e);
        }
        return new ResponseEntity<>(
                this.buildResponse(ExceptionResponse.CODES.GLOBAL_EXCEPTION.getCode(), ExceptionResponse.CODES.GLOBAL_EXCEPTION.getMessage()),
                ExceptionResponse.CODES.GLOBAL_EXCEPTION.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ TechnicalException.class })
    public ResponseEntity<Object> handleTechnicalException(TechnicalException e, WebRequest request) {
        // Toutes les erreurs internes du serveur sont tracées
        if (_LOGGER.isErrorEnabled()) {
            _LOGGER.error("Une erreur technique interne s'est produite exception", e);
        }
        ExceptionResponse response = this.buildResponse(e.getCode(), e.getMessage(), e.getContextParams());
        return new ResponseEntity<>(response, ExceptionResponse.CODES.UNEXPECTED.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ HttpFunctionalException.class })
    public ResponseEntity<Object> handleHttpFunctionalException(HttpFunctionalException e, WebRequest request) {
        // Toutes les erreurs internes du serveur sont tracées
        if (_LOGGER.isDebugEnabled())
            _LOGGER.debug("Une erreur fonctionnelle web interne s'est produite exception", e);
        ExceptionResponse response = this.buildResponse(e.getCode(), e.getMessage(), e.getContextParams());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ FunctionalException.class })
    public ResponseEntity<Object> handleFunctionalException(FunctionalException e, WebRequest request) {
        // Toutes les erreurs internes du serveur sont tracées
        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("Une erreur technique interne s'est produite exception", e);
        }
        ExceptionResponse response = this.buildResponse(e.getCode(), e.getMessage(), e.getContextParams());
        return new ResponseEntity<>(response, ExceptionResponse.CODES.UNEXPECTED.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        final List<ExceptionResponse> responses = new ArrayList<>();
        // On parcourt toutes les erreurs
        for (final FieldError error : e.getBindingResult().getFieldErrors()) {
            _LOGGER.debug("FieldError : {} :: {} : {}", error.getCode(), error.getField(), error.getDefaultMessage());
            responses.add(new ExceptionResponse(error.getCode(), error.getDefaultMessage()));
        }
        for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
            _LOGGER.debug("ObjectError : {} :: {} : {}", error.getCode(), error.getObjectName(), error.getDefaultMessage());
            responses.add(new ExceptionResponse(error.getCode(), error.getDefaultMessage()));
        }
        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("handleMethodArgumentNotValid exception", e);
        }
        return handleExceptionInternal(e, responses, httpHeader, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String[] ltSupported = exception.getSupportedMethods();
        if (ltSupported == null) {
            ltSupported = new String[0];
        }

        ExceptionResponse response =
                this.buildResponse(ExceptionResponse.CODES.METHOD_NOT_SUPPORTED.getCode(), ExceptionResponse.CODES.METHOD_NOT_SUPPORTED.getMessage(),
                        Map.of("supportedmethods", String.join(DELIMITER, ltSupported)));

        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("handleMethodArgumentNotValid exception", exception);
        }
        return new ResponseEntity<>(response, ExceptionResponse.CODES.METHOD_NOT_SUPPORTED.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        String url = exception.getRequestURL();
        String method = exception.getHttpMethod();
        if (_LOGGER.isDebugEnabled()) {
            _LOGGER.debug("handleNoHandlerFoundException exception", exception);
        }
        ExceptionResponse response =
                this.buildResponse(ExceptionResponse.CODES.NO_HANDLE_FOUND.getCode(), ExceptionResponse.CODES.NO_HANDLE_FOUND.getMessage(),
                        Map.of("url", url, "method", method));

        return new ResponseEntity<>(response, ExceptionResponse.CODES.NO_HANDLE_FOUND.getHttpStatus());
    }

}
