package io.realworld.backend.app.exception;

public class InvalidPasswordException extends InvalidRequestException {
  public InvalidPasswordException(String message) {
    super(message);
  }
}
