package io.realworld.backend.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.realworld.backend.app.exception.UserNotFoundException;
import io.realworld.backend.app.service.AuthenticationService;
import io.realworld.backend.domain.model.user.User;

public abstract class BaseService {
  public <T> ResponseEntity<T> ok(T body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }

  /** Returns AuthenticationService. */
  public abstract AuthenticationService getAuthenticationService();

  /**
   * Returns current user or throws an exceptions.
   *
   * @return current user
   * @throws UserNotFoundException if the current is anonymous
   */
  public User currentUserOrThrow() {
    return getAuthenticationService()
        .getCurrentUser()
        .orElseThrow(() -> new UserNotFoundException("Can not authenticate"));
  }
}
