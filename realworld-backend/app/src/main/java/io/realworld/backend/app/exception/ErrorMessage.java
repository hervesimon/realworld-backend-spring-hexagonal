package io.realworld.backend.app.exception;

/**
 * @author Bgegout
 * Enumération des clés des codes et messages des erreurs remontées
 */
public enum ErrorMessage {

    UNEXPECTED("produit.erreur.innatendue.code", "produit.erreur.innatendue"),
    DATABASE_ERROR("produit.erreur.bdd.code", "produit.erreur.bdd"),
    NOT_FOUND("produit.introuvable.code", "produit.introuvable"),
    BAD_REQUEST("common.erreur.requete.code", "common.erreur.requete");

    // La clé du code de l'erreur
    private final String codeKey;
    // La clé du message de l'erreur
    private final String messageKey;

    ErrorMessage(final String codeKey, final String messageKey) {
        this.codeKey = codeKey;
        this.messageKey = messageKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
