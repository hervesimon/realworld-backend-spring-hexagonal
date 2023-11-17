package io.realworld.backend.domain.repository;

import java.util.List;

import io.realworld.backend.domain.model.follow.FollowRelation;

public interface FollowRelationRepository {
  List<FollowRelation> findByIdFollowerId(long followerId);
}
