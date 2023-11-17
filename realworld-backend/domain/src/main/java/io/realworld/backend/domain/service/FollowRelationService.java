package io.realworld.backend.domain.service;

import java.util.List;

import io.realworld.backend.domain.model.follow.FollowRelation;

public interface FollowRelationService {
    List<FollowRelation> findByIdFollowerId(long followerId);
}
