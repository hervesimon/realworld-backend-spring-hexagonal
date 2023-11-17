package io.realworld.backend.domain.model.favourite;

public class FavouriteCount {
    protected final long articleId;
    protected final long count;

    public FavouriteCount(long articleId, long count) {
        this.articleId = articleId;
        this.count = count;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getCount() {
        return count;
    }
}
