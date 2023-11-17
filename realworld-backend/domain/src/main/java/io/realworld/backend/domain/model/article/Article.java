package io.realworld.backend.domain.model.article;

import java.time.Instant;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

import io.realworld.backend.domain.model.user.User;

public class Article {
    private long id = 0;

    private String slug = "";
    private String title = "";
    private String description = "";
    private String body = "";

    private Set<String> tags = Set.of();

    private User author = new User("", "", "");
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    /** Sets title and generate a slug. */
    public void setTitle(String title) {
        this.slug = title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-") + "-" + ThreadLocalRandom.current().nextInt();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = Set.copyOf(tags);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public void onUpdate() {
        updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Article.class.getSimpleName() + "[", "]").add("id=" + id)
                                                                               .add("slug='" + slug + "'")
                                                                               .add("title='" + title + "'")
                                                                               .add("description='" + description + "'")
                                                                               .add("body='" + body + "'")
                                                                               .add("tags=" + tags)
                                                                               .add("author=" + author)
                                                                               .add("createdAt=" + createdAt)
                                                                               .add("updatedAt=" + updatedAt)
                                                                               .toString();
    }
}
