package io.realworld.backend.domain.model.comment;

import java.time.Instant;
import java.util.StringJoiner;

import io.realworld.backend.domain.model.article.Article;
import io.realworld.backend.domain.model.user.User;

public class Comment {
    private long id = 0;

    private Article article = new Article();

    private User author = new User("", "", "");

    private String body = "";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Comment.class.getSimpleName() + "[", "]").add("id=" + id)
                                                                               .add("article=" + article)
                                                                               .add("author=" + author)
                                                                               .add("body='" + body + "'")
                                                                               .add("createdAt=" + createdAt)
                                                                               .add("updatedAt=" + updatedAt)
                                                                               .toString();
    }
}
