package io.realworld.backend.domain.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import io.realworld.backend.domain.model.article.Article;
import io.realworld.backend.domain.model.pagination.Page;
import io.realworld.backend.domain.service.ArticleService;

public class ArticleImplService implements ArticleService {

    @Override
    public Optional<Article> findBySlug(String slug) {
        return Optional.empty();
    }

    @Override
    public List<Article> findByAuthorIdIn(Collection<Long> authorIds, Page pageable) {
        return null;
    }

    @Override
    public List<Article> findByFilters(String tag, String author, String favorited, Page pageable) {
        return null;
    }

    @Override
    public int countByFilter(String tag, String author, String favorited) {
        return 0;
    }

    @Override
    public int countByAuthorIdIn(Collection<Long> authorIds) {
        return 0;
    }

    @Override
    public List<String> findAllTags() {
        return null;
    }
}
