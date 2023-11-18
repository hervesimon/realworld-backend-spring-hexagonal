package io.realworld.backend.app.service;

import static io.realworld.backend.app.mapper.Mappers.toProfileResponse;

import io.realworld.backend.app.exception.UserNotFoundException;
import io.realworld.backend.app.util.BaseService;
import io.realworld.backend.domain.model.follow.FollowRelationId;
import io.realworld.backend.domain.service.ProfilService;
import io.realworld.backend.domain.service.UserService;
import io.realworld.backend.rest.api.ProfilesApiDelegate;
import io.realworld.backend.rest.api.model.ProfileResponseJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileApiDelegateImpl extends BaseService implements ProfilesApiDelegate {

  private final ProfilService profilService;
  private final UserService userService;
  private final AuthenticationService authenticationService;

  /** Creates ProfileService instance. */
  @Autowired
  public ProfileApiDelegateImpl(
      ProfilService profilService,
      UserService userService,
      AuthenticationService authenticationService) {
    this.profilService = profilService;
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<ProfileResponseJson> followUserByUsername(String username) {
    final var currentUser = currentUserOrThrow();

    final var user =
        userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

    userService
        .findByUsername(username)
        .map(
            foundUser ->
                profilService.followUserByUsername(
                    new FollowRelationId(currentUser.getId(), foundUser.getId())))
        .orElseThrow(() -> new UserNotFoundException(username));

    //    final var followRelationId = new FollowRelationId(currentUser.getId(), user.getId());
    //    followRelationRepository
    //        .findById(followRelationId)
    //        .or(
    //            () -> {
    //              final var followRelation = new FollowRelation(currentUser.getId(),
    // user.getId());
    //              followRelationRepository.save(followRelation);
    //              return Optional.of(followRelation);
    //            });

    return ok(toProfileResponse(user, true));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<ProfileResponseJson> getProfileByUsername(String username) {
    final var currentUser = currentUserOrThrow();

    final ProfileResponseJson profileResponseJson =
        userService
            .findByUsername(username)
            .map(
                foundUser ->
                    toProfileResponse(
                        foundUser,
                        profilService.isFollowing(
                            new FollowRelationId(currentUser.getId(), foundUser.getId()))))
            .orElseThrow(() -> new UserNotFoundException(username));

    return ok(profileResponseJson);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<ProfileResponseJson> unfollowUserByUsername(String username) {
    final var currentUser = currentUserOrThrow();

    final var user =
        userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    final var followRelationId = new FollowRelationId(currentUser.getId(), user.getId());
    profilService.unfollowUserByUsername(followRelationId);

    // Todo a reporter dans le service
    //    followRelationRepository.deleteById(followRelationId);

    return ok(toProfileResponse(user, false));
  }

  /** {@inheritDoc} */
  @Override
  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }
}
