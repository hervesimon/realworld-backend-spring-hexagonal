package io.realworld.backend.app.exception;

public class UsernameAlreadyUsedException extends InvalidRequestException {

  public UsernameAlreadyUsedException(String message) {
    super(message);
  }
}
