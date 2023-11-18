package io.realworld.backend.app.service;

import static io.realworld.backend.app.mapper.Mappers.toUserResponse;

import io.realworld.backend.app.exception.EmailAlreadyUsedException;
import io.realworld.backend.app.exception.InvalidPasswordException;
import io.realworld.backend.app.exception.UserNotFoundException;
import io.realworld.backend.app.exception.UsernameAlreadyUsedException;
import io.realworld.backend.app.mapper.Mappers;
import io.realworld.backend.app.util.BaseService;
import io.realworld.backend.domain.model.user.User;
import io.realworld.backend.domain.service.UserService;
import io.realworld.backend.rest.api.UserApiDelegate;
import io.realworld.backend.rest.api.UsersApiDelegate;
import io.realworld.backend.rest.api.model.LoginUserRequestJson;
import io.realworld.backend.rest.api.model.NewUserRequestJson;
import io.realworld.backend.rest.api.model.UpdateUserRequestJson;
import io.realworld.backend.rest.api.model.UserResponseJson;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

@Service
@Transactional
public class UserApiDelegateImpl extends BaseService implements UserApiDelegate, UsersApiDelegate {
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationService authenticationService;

  /** Creates ApiFacade instance. */
  @Autowired
  public UserApiDelegateImpl(
      UserService userService, JwtService jwtService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<UserResponseJson> createUser(NewUserRequestJson req) {
    final var newUserJson = req.getUser();
    String username = newUserJson.getUsername();
    String email = newUserJson.getEmail();
    userService
        .findByUsername(username)
        .ifPresent(
            u -> {
              throw new UsernameAlreadyUsedException("Username already used - " + username);
            });
    userService
        .findByEmail(email)
        .ifPresent(
            u -> {
              throw new EmailAlreadyUsedException("Email already used - " + email);
            });
    final var newUser =
        new User(email, username, authenticationService.encodePassword(newUserJson.getPassword()));
    final var user = userService.save(newUser);
    return ok(toUserResponse(user, jwtService.generateToken(user)));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<UserResponseJson> getCurrentUser() {
    return authenticationService
        .getCurrentUser()
        .map(u -> ok(toUserResponse(u, authenticationService.getCurrentToken().orElse(""))))
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<UserResponseJson> updateCurrentUser(UpdateUserRequestJson req) {
    final var user =
        authenticationService
            .getCurrentUser()
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    final var update = req.getUser();
    final var email = update.getEmail();
    if (email != null && !email.equals(user.getEmail())) {
      userService
          .findByEmail(email)
          .ifPresent(
              u -> {
                throw new EmailAlreadyUsedException("Email already used - " + email);
              });
    }
    final var username = update.getUsername();
    if (username != null && !username.equals(user.getUsername())) {
      userService
          .findByUsername(username)
          .ifPresent(
              u -> {
                throw new UsernameAlreadyUsedException("Username already used - " + username);
              });
    }
    Mappers.updateUser(user, update);

    return ok(toUserResponse(user, authenticationService.getCurrentToken().orElse("")));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<UserResponseJson> login(LoginUserRequestJson body) {
    final var loginUserJson = body.getUser();
    final var email = loginUserJson.getEmail();
    return authenticationService
        .authenticate(loginUserJson.getEmail(), loginUserJson.getPassword())
        .map(u -> ok(toUserResponse(u, jwtService.generateToken(u))))
        .orElseThrow(() -> new InvalidPasswordException("Can not authenticate - " + email));
  }

  /** {@inheritDoc} */
  @Override
  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }
}
