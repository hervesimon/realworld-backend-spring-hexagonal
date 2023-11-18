package io.realworld.backend.domain.exception;

import java.util.Map;

/**
 * Interface exposant les éléments de base d'une exception
 */
public interface BaseException {

    /**
     * Code identifiant le contenu de l'exception
     *
     * @return
     */
    String getCode();

    /**
     * le message détail
     *
     * @return
     */
    String getMessage();

    /**
     * les parametres de contexte
     *
     * @return
     */
    Map<String, Object> getContextParams();

    /**
     * La cause
     *
     * @return
     */
    Throwable getCause();

}
