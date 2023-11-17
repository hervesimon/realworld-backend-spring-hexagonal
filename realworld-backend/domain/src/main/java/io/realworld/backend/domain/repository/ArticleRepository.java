package io.realworld.backend.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import io.realworld.backend.domain.model.pagination.Page;
import io.realworld.backend.domain.model.article.Article;

public interface ArticleRepository {
  Optional<Article> findBySlug(String slug);

  List<Article> findByAuthorIdIn(Collection<Long> authorIds, Page pageable);

  List<Article> findByFilters(String tag, String author, String favorited, Page pageable);

  int countByFilter(String tag, String author, String favorited);

  int countByAuthorIdIn(Collection<Long> authorIds);

  List<String> findAllTags();
}
