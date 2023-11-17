package io.realworld.backend.domain.model.favourite;

import java.util.StringJoiner;

public class ArticleFavourite {
    private ArticleFavouriteId id = new ArticleFavouriteId(0, 0);

    private ArticleFavourite() {
    }

    public ArticleFavourite(long userId, long articleId) {
        this.id = new ArticleFavouriteId(userId, articleId);
    }

    public ArticleFavouriteId getId() {
        return id;
    }

    public void setId(ArticleFavouriteId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ArticleFavourite.class.getSimpleName() + "[", "]").add("id=" + id).toString();
    }
}
