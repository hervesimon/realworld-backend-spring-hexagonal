package io.realworld.backend.app.exception;

public class UserNotFoundException extends InvalidRequestException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
