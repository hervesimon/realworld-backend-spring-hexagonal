package io.realworld.backend.domain.service.impl;

import java.util.List;

import io.realworld.backend.domain.model.favourite.ArticleFavourite;
import io.realworld.backend.domain.model.favourite.FavouriteCount;
import io.realworld.backend.domain.service.ArticleFavoriteService;

public class ArticleFavoriteImplService implements ArticleFavoriteService {
    @Override
    public int countByIdArticleId(long articleId) {
        return 0;
    }

    @Override
    public List<FavouriteCount> countByIdArticleIds(List<Long> articleIds) {
        return null;
    }

    @Override
    public List<ArticleFavourite> findByIdUserId(long userId) {
        return null;
    }
}
