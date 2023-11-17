package io.realworld.backend.domain.service.impl;

import java.util.Optional;

import io.realworld.backend.domain.model.user.User;
import io.realworld.backend.domain.service.UserService;

public class UserImplService implements UserService {
    @Override
    public Optional<User> findByEmail(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
