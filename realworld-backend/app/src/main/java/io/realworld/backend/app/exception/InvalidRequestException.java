package io.realworld.backend.app.exception;

public abstract class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(String message) {
    super(message);
  }
}
