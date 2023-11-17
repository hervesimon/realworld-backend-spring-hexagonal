package io.realworld.backend.domain.model.follow;

import java.util.StringJoiner;

public class FollowRelation {
    private FollowRelationId id = new FollowRelationId(0, 0);

    protected FollowRelation() {
    }

    public FollowRelation(long followerId, long followeeId) {
        this.id = new FollowRelationId(followerId, followeeId);
    }

    public FollowRelationId getId() {
        return id;
    }

    public void setId(FollowRelationId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FollowRelation.class.getSimpleName() + "[", "]").add("id=" + id).toString();
    }
}
