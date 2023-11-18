package io.realworld.backend.app.service;

import java.util.Optional;

import io.realworld.backend.domain.model.user.User;

public interface AuthenticationService {
  /** Returns current authenticated user. */
  Optional<User> getCurrentUser();

  /** Returns a JWT token used to authenticate current user. */
  Optional<String> getCurrentToken();

  /** Checks if provided credentials correct and returns relevant user. */
  Optional<User> authenticate(String email, String password);

  /** Returns a hash of the password. */
  String encodePassword(String password);
}
