package io.realworld.backend.app.service;

import java.util.Optional;

import io.realworld.backend.domain.aggregate.user.User;

public interface JwtService {
  /** Generates JWT token for a given user. */
  String generateToken(User user);

  /** Finds a user that given token was generated for. */
  Optional<User> getUser(String token);
}
