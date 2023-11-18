package io.realworld.backend.app.service;

import io.realworld.backend.app.exception.ArticleNotFoundException;
import io.realworld.backend.app.mapper.Mappers;
import io.realworld.backend.app.util.BaseService;
import io.realworld.backend.domain.model.article.Article;
import io.realworld.backend.domain.model.favourite.FavouriteCount;
import io.realworld.backend.domain.model.follow.FollowRelationId;
import io.realworld.backend.domain.model.pagination.OffsetBasedPageRequest;
import io.realworld.backend.domain.model.pagination.Sort;
import io.realworld.backend.domain.service.ArticleService;
import io.realworld.backend.domain.service.ProfilService;
import io.realworld.backend.rest.api.ArticlesApiDelegate;
import io.realworld.backend.rest.api.TagsApiDelegate;
import io.realworld.backend.rest.api.model.MultipleArticlesResponseJson;
import io.realworld.backend.rest.api.model.MultipleCommentsResponseJson;
import io.realworld.backend.rest.api.model.NewArticleRequestJson;
import io.realworld.backend.rest.api.model.NewCommentRequestJson;
import io.realworld.backend.rest.api.model.SingleArticleResponseJson;
import io.realworld.backend.rest.api.model.SingleCommentResponseJson;
import io.realworld.backend.rest.api.model.TagsResponseJson;
import io.realworld.backend.rest.api.model.UpdateArticleRequestJson;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

@Service
@Transactional
public class ArticleApiDelegateImpl extends BaseService
    implements ArticlesApiDelegate, TagsApiDelegate {
  private final ArticleService articleService;
  private final ProfilService profilService;
  private final AuthenticationService authenticationService;

  /** Creates ArticleService instance. */
  @SuppressWarnings("PMD.ExcessiveParameterList")
  @Autowired
  public ArticleApiDelegateImpl(
      ArticleService articleService,
      ProfilService profilService,
      AuthenticationService authenticationService) {
    this.articleService = articleService;
    this.profilService = profilService;
    this.authenticationService = authenticationService;
  }

  /** {@inheritDoc} */
  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleArticleResponseJson> createArticle(NewArticleRequestJson req) {
    final var currentUser = currentUserOrThrow();

    final var newArticleJson = req.getArticle();
    final var article = Mappers.fromNewArticleJson(newArticleJson, currentUser);

    return articleResponse(articleService.createArticle(article));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleArticleResponseJson> getArticle(String slug) {
    return articleService
        .getArticle(slug)
        .map(this::articleResponse)
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleArticleResponseJson> updateArticle(
      String slug, UpdateArticleRequestJson req) {
    return articleService
        .updateArticle(Mappers.updateArticle(new Article(), req.getArticle()))
        .map(article -> articleResponse(article))
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<Void> deleteArticle(String slug) {
    articleService.deleteArticle(slug);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleArticleResponseJson> createArticleFavorite(String slug) {
    final var currentUser = currentUserOrThrow();
    return articleService
        .createArticleFavorite(slug, currentUser.getId())
        .map(article -> articleResponse(article))
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleArticleResponseJson> deleteArticleFavorite(String slug) {
    final var currentUser = currentUserOrThrow();
    return articleService
        .deleteArticleFavorite(slug, currentUser.getId())
        .map(article -> articleResponse(article))
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<SingleCommentResponseJson> createArticleComment(
      String slug, NewCommentRequestJson commentJson) {
    final var currentUser = currentUserOrThrow();
    return articleService
        .getArticle(slug)
        .map(
            article -> {
              final var isFollowingAuthor = isFollowingAuthor(article);
              final var comment = Mappers.fromNewCommentJson(commentJson.getComment(), article, currentUser);
              return ok(Mappers.toSingleCommentResponseJson(
                      articleService.createArticleComment(comment), isFollowingAuthor));
            })
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<Void> deleteArticleComment(String slug, Integer id) {
    articleService.deleteArticleComment(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<MultipleCommentsResponseJson> getArticleComments(String slug) {
    return articleService
        .getArticleComments(slug)
        .map(comments -> ok(Mappers.toMultipleCommentsResponseJson(comments, followingIds())))
        .orElseThrow(() -> new ArticleNotFoundException(slug));
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<MultipleArticlesResponseJson> getArticlesFeed(
      Integer limit, Integer offset) {
    final var followingIds = followingIds();
    final var articles = articleService.getArticlesFeed(followingIds, OffsetBasedPageRequest.of(offset, limit, Sort.by(
            Sort.Direction.DESC, "createdAt")));
    final var articleCount = articleService.countByAuthorIdIn(followingIds);
    return articlesResponse(articles, articleCount);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("PMD.ExcessiveParameterList")
  @Override
  public ResponseEntity<MultipleArticlesResponseJson> getArticles(
      String tag, String author, String favorited, Integer limit, Integer offset) {
    final var articles =
        articleService.getArticles(
            tag,
            author,
            favorited,
            OffsetBasedPageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
    final var articleCount = articleService.countByFilter(tag, author, favorited);
    return articlesResponse(articles, articleCount);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseEntity<TagsResponseJson> tagsGet() {
    return ok(Mappers.toTagsResponseJson(articleService.findAllTags()));
  }

  private ResponseEntity<MultipleArticlesResponseJson> articlesResponse(
      List<Article> articles, int articleCount) {
    final var currentUser = currentUserOrThrow();
    final var articleIds = articles.stream().map(Article::getId).collect(Collectors.toList());
    final var favouritedCounts =
        articleService.getFavouritedCounts(articleIds).stream()
            .collect(Collectors.groupingBy(FavouriteCount::getArticleId, Collectors.counting()));
    final var favourited =
        articleService.getFavouritedByUser(currentUser.getId()).stream()
            .map(f -> f.getId().getArticleId())
            .collect(Collectors.toSet());
    final var favouriteInfo = new Mappers.MultipleFavouriteInfo(favourited, favouritedCounts);
    return ok(
        Mappers.toMultipleArticlesResponseJson(
            articles, favouriteInfo, followingIds(), articleCount));
  }

  private ResponseEntity<SingleArticleResponseJson> articleResponse(Article article) {
      final var currentUser = currentUserOrThrow();
    final var isFollowingAuthor = isFollowingAuthor(article);
    final var isFavoured = articleService.isCurrentUserFavorite(currentUser.getId(), article.getId());
    final var favouritesCount = articleService.getFavouritedCount(article.getId());
    final var favouriteInfo = new Mappers.FavouriteInfo(isFavoured, favouritesCount);
    return ok(Mappers.toSingleArticleResponse(article, favouriteInfo, isFollowingAuthor));
  }

  private boolean isFollowingAuthor(Article article) {
    return getAuthenticationService()
        .getCurrentUser()
        .map(
            currentUser ->
                profilService.isFollowing(
                    new FollowRelationId(currentUser.getId(), article.getAuthor().getId())))
        .orElse(false);
  }

  private Set<Long> followingIds() {
    return getAuthenticationService()
        .getCurrentUser()
        .map(
            currentUser ->
                profilService.findFollowRelations(currentUser.getId()).stream()
                    .map(f -> f.getId().getFolloweeId())
                    .collect(Collectors.toSet()))
        .orElse(Collections.emptySet());
  }

  /** {@inheritDoc} */
  @Override
  public AuthenticationService getAuthenticationService() {
    return authenticationService;
  }
}
