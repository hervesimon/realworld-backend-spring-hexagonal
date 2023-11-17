package io.realworld.backend.domain.repository;

import java.util.Optional;

import io.realworld.backend.domain.model.user.User;

public interface UserRepository {
  Optional<User> findByEmail(String username);

  Optional<User> findByUsername(String username);
}
