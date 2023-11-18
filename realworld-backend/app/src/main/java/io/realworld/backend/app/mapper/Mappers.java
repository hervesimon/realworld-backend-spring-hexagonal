package io.realworld.backend.app.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.realworld.backend.domain.model.article.Article;
import io.realworld.backend.domain.model.comment.Comment;
import io.realworld.backend.domain.model.user.User;
import io.realworld.backend.rest.api.model.ArticleJson;
import io.realworld.backend.rest.api.model.CommentJson;
import io.realworld.backend.rest.api.model.MultipleArticlesResponseJson;
import io.realworld.backend.rest.api.model.MultipleCommentsResponseJson;
import io.realworld.backend.rest.api.model.NewArticleJson;
import io.realworld.backend.rest.api.model.NewCommentJson;
import io.realworld.backend.rest.api.model.ProfileJson;
import io.realworld.backend.rest.api.model.ProfileResponseJson;
import io.realworld.backend.rest.api.model.SingleArticleResponseJson;
import io.realworld.backend.rest.api.model.SingleCommentResponseJson;
import io.realworld.backend.rest.api.model.TagsResponseJson;
import io.realworld.backend.rest.api.model.UpdateArticleJson;
import io.realworld.backend.rest.api.model.UpdateUserJson;
import io.realworld.backend.rest.api.model.UserJson;
import io.realworld.backend.rest.api.model.UserResponseJson;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Mappers {

  /** Constructs UserResponseJson response. */
  public static UserResponseJson toUserResponse(User u, String token) {
    final var userJson = new UserJson();
    userJson.setUsername(u.getUsername());
    userJson.setEmail(u.getEmail());
    u.getBio().ifPresent(userJson::setBio);
    u.getImage().ifPresent(userJson::setImage);
    if (token != null) {
      userJson.setToken(token);
    }
    final var res = new UserResponseJson();
    res.setUser(userJson);
    return res;
  }

  /** Updates user. * */
  public static void updateUser(User user, UpdateUserJson update) {
    final var email = update.getEmail();
    if (email != null) {
      user.setEmail(email);
    }
    final var username = update.getUsername();
    if (username != null) {
      user.setUsername(username);
    }
    final var bio = update.getBio();
    if (bio != null) {
      user.setBio(bio);
    }
    final var image = update.getImage();
    if (image != null) {
      user.setImage(image);
    }
  }

  /** Constructs ProfileResponseJson response. */
  public static ProfileResponseJson toProfileResponse(User user, boolean isFollowing) {
    final var profileResponse = new ProfileResponseJson();
    final ProfileJson profile = toProfile(user, isFollowing);
    profileResponse.setProfile(profile);
    return profileResponse;
  }

  /** Constructs ProfileJson response. */
  public static ProfileJson toProfile(User user, boolean isFollowing) {
    final var profile = new ProfileJson();
    profile.setUsername(user.getUsername());
    user.getBio().ifPresent(profile::setBio);
    user.getImage().ifPresent(profile::setImage);
    profile.setFollowing(isFollowing);
    return profile;
  }

  public static class FavouriteInfo {
    private final boolean isFavorited;
    private final int favoritesCount;

    public FavouriteInfo(boolean isFavorited, int favoritesCount) {
      this.isFavorited = isFavorited;
      this.favoritesCount = favoritesCount;
    }

    public boolean isFavorited() {
      return isFavorited;
    }

    public int getFavoritesCount() {
      return favoritesCount;
    }
  }

  /** Constructs SingleArticleResponseJson response. */
  public static SingleArticleResponseJson toSingleArticleResponse(
      Article article, FavouriteInfo favouriteInfo, boolean isFollowingAuthor) {
    final var resp = new SingleArticleResponseJson();
    final ArticleJson articleJson = toArticleJson(article, favouriteInfo, isFollowingAuthor);
    resp.setArticle(articleJson);
    return resp;
  }

  /** Constructs Article from the request. */
  public static Article fromNewArticleJson(NewArticleJson newArticleJson, User user) {
    final var article = new Article();
    article.setTitle(newArticleJson.getTitle());
    article.setDescription(newArticleJson.getDescription());
    article.setBody(newArticleJson.getBody());
    article.setAuthor(user);
    article.setTags(ImmutableSet.copyOf(newArticleJson.getTagList()));
    return article;
  }

  /** Updates article. */
  public static Article updateArticle(Article article, UpdateArticleJson updateArticleJson) {
    final var title = updateArticleJson.getTitle();
    if (title != null) {
      article.setTitle(title);
    }
    final var description = updateArticleJson.getDescription();
    if (description != null) {
      article.setDescription(description);
    }
    final var body = updateArticleJson.getBody();
    if (body != null) {
      article.setBody(body);
    }

    return article;
  }

  /** Constructs SingleCommentResponseJson response. */
  public static SingleCommentResponseJson toSingleCommentResponseJson(
      Comment comment, boolean isFollowingAuthor) {
    final var resp = new SingleCommentResponseJson();
    final var commentJson = new CommentJson();
    commentJson.setId((int) comment.getId());
    commentJson.setAuthor(toProfile(comment.getAuthor(), isFollowingAuthor));
    commentJson.setBody(comment.getBody());
    commentJson.setCreatedAt(comment.getCreatedAt().atOffset(ZoneOffset.UTC));
    commentJson.setUpdatedAt(comment.getUpdatedAt().atOffset(ZoneOffset.UTC));
    resp.setComment(commentJson);
    return resp;
  }

  /** Constructs Comment from the request. */
  public static Comment fromNewCommentJson(
      NewCommentJson commentJson, Article article, User author) {
    final var comment = new Comment();
    comment.setArticle(article);
    comment.setAuthor(author);
    comment.setBody(commentJson.getBody());
    return comment;
  }

  /** Constructs MultipleCommentsResponseJson response. */
  public static MultipleCommentsResponseJson toMultipleCommentsResponseJson(
      Collection<Comment> comments, Set<Long> followingIds) {
    final var commentsResponseJson = new MultipleCommentsResponseJson();
    final var commentJsonList =
        comments.stream()
            .map(
                c -> {
                  final var commentJson = new CommentJson();
                  commentJson.setId((int) c.getId());
                  commentJson.setBody(c.getBody());
                  commentJson.setAuthor(
                      toProfile(c.getAuthor(), followingIds.contains(c.getAuthor().getId())));
                  commentJson.setCreatedAt(c.getCreatedAt().atOffset(ZoneOffset.UTC));
                  commentJson.setUpdatedAt(c.getUpdatedAt().atOffset(ZoneOffset.UTC));
                  return commentJson;
                })
            .collect(Collectors.toList());
    commentsResponseJson.setComments(commentJsonList);
    return commentsResponseJson;
  }

  public static class MultipleFavouriteInfo {
    private final Set<Long> favouritedArticleIds;
    private final Map<Long, Long> favouritedCountByArticleId;

    public MultipleFavouriteInfo(
        Set<Long> favouritedArticleIds, Map<Long, Long> favouritedCountByArticleId) {
      this.favouritedArticleIds = favouritedArticleIds;
      this.favouritedCountByArticleId = favouritedCountByArticleId;
    }

    public Set<Long> getFavouritedArticleIds() {
      return favouritedArticleIds;
    }

    public Map<Long, Long> getFavouritedCountByArticleId() {
      return favouritedCountByArticleId;
    }
  }

  /** Constructs MultipleArticlesResponseJson response. */
  public static MultipleArticlesResponseJson toMultipleArticlesResponseJson(
      Collection<Article> articles,
      MultipleFavouriteInfo multipleFavouriteInfo,
      Set<Long> followingIds,
      int count) {
    final var multipleArticlesResponseJson = new MultipleArticlesResponseJson();
    final var articleJsonList =
        articles.stream()
            .map(
                article ->
                    toArticleJson(
                        article,
                        new FavouriteInfo(
                            multipleFavouriteInfo
                                .getFavouritedArticleIds()
                                .contains(article.getId()),
                            multipleFavouriteInfo
                                .getFavouritedCountByArticleId()
                                .getOrDefault(article.getId(), 0L)
                                .intValue()),
                        followingIds.contains(article.getAuthor().getId())))
            .collect(Collectors.toList());
    multipleArticlesResponseJson.setArticles(articleJsonList);
    multipleArticlesResponseJson.setArticlesCount(count);
    return multipleArticlesResponseJson;
  }

  /** Constructs TagsResponseJson response. */
  public static TagsResponseJson toTagsResponseJson(List<String> tags) {
    final var tagsResponseJson = new TagsResponseJson();
    tagsResponseJson.setTags(tags);
    return tagsResponseJson;
  }

  private static ArticleJson toArticleJson(
      Article article, FavouriteInfo favouriteInfo, boolean isFollowingAuthor) {
    final var articleJson = new ArticleJson();
    articleJson.setSlug(article.getSlug());
    articleJson.setTitle(article.getTitle());
    articleJson.setDescription(article.getDescription());
    articleJson.setBody(article.getBody());
    articleJson.setTagList(ImmutableList.copyOf(article.getTags()));
    articleJson.setCreatedAt(article.getCreatedAt().atOffset(ZoneOffset.UTC));
    articleJson.setUpdatedAt(article.getUpdatedAt().atOffset(ZoneOffset.UTC));
    articleJson.setFavorited(favouriteInfo.isFavorited());
    articleJson.setFavoritesCount(favouriteInfo.getFavoritesCount());
    articleJson.setAuthor(toProfile(article.getAuthor(), isFollowingAuthor));
    return articleJson;
  }
}
