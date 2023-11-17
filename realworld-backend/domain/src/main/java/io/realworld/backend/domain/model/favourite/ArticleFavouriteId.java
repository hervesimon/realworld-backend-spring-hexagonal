package io.realworld.backend.domain.model.favourite;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class ArticleFavouriteId implements Serializable {
    private long userId;
    private long articleId;

    protected ArticleFavouriteId() {
    }

    public ArticleFavouriteId(long userId, long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleFavouriteId that = (ArticleFavouriteId) o;
        return userId == that.userId && articleId == that.articleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, articleId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ArticleFavouriteId.class.getSimpleName() + "[", "]").add("userId=" + userId).add("articleId=" + articleId).toString();
    }
}
