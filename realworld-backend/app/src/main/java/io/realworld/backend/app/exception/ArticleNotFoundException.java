package io.realworld.backend.app.exception;

public class ArticleNotFoundException extends InvalidRequestException {

  public ArticleNotFoundException(String message) {
    super(message);
  }
}
