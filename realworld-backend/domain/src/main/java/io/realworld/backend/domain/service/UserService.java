package io.realworld.backend.domain.service;

import java.util.Optional;

import io.realworld.backend.domain.model.user.User;

public interface UserService {

    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);
}
