package io.realworld.backend.app.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.realworld.backend.app.service.AuthenticationService;
import io.realworld.backend.domain.model.user.User;
import io.realworld.backend.domain.repository.UserRepository;

@Service
public class SpringAuthenticationService implements AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SpringAuthenticationService(
      UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<User> getCurrentUser() {
    final var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return Optional.empty();
    }
    if (authentication.getPrincipal().equals("anonymousUser")) {
      return Optional.empty();
    }
    final var ud = (UserDetails) authentication.getPrincipal();
    return userRepository.findByEmail(ud.getUsername());
  }

  /** {@inheritDoc} */
  @Override
  public Optional<String> getCurrentToken() {
    final var authentication = SecurityContextHolder.getContext().getAuthentication();
    return Optional.ofNullable((String) authentication.getCredentials());
  }

  /** {@inheritDoc} */
  @Override
  public Optional<User> authenticate(String email, String password) {
    return userRepository
        .findByEmail(email)
        .flatMap(
            u -> {
              if (passwordEncoder.matches(password, u.getPasswordHash())) {
                return Optional.of(u);
              } else {
                return Optional.empty();
              }
            });
  }

  /** {@inheritDoc} */
  @Override
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}
