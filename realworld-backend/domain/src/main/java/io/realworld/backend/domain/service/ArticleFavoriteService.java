package io.realworld.backend.domain.service;

import java.util.List;

import io.realworld.backend.domain.model.favourite.ArticleFavourite;
import io.realworld.backend.domain.model.favourite.FavouriteCount;

public interface ArticleFavoriteService {

    int countByIdArticleId(long articleId);

    List<FavouriteCount> countByIdArticleIds(List<Long> articleIds);

    List<ArticleFavourite> findByIdUserId(long userId);
}
