package io.realworld.backend.app.exception;

public class EmailAlreadyUsedException extends InvalidRequestException {

  public EmailAlreadyUsedException(String message) {
    super(message);
  }
}
