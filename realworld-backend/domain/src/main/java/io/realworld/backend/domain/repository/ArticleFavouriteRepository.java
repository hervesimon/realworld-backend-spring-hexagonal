package io.realworld.backend.domain.repository;

import java.util.List;

import io.realworld.backend.domain.model.favourite.ArticleFavourite;
import io.realworld.backend.domain.model.favourite.FavouriteCount;

public interface ArticleFavouriteRepository {
  int countByIdArticleId(long articleId);

  List<FavouriteCount> countByIdArticleIds(List<Long> articleIds);

  List<ArticleFavourite> findByIdUserId(long userId);
}
