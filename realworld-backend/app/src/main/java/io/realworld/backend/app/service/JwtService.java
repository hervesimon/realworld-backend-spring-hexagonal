package io.realworld.backend.app.service;

import io.realworld.backend.domain.model.user.User;
import java.util.Optional;

public interface JwtService {
  /** Generates JWT token for a given user. */
  String generateToken(User user);

  /** Finds a user that given token was generated for. */
  Optional<User> getUser(String token);
}
