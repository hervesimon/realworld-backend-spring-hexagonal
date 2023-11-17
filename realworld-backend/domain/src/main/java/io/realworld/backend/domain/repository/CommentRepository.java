package io.realworld.backend.domain.repository;

import java.util.List;

import io.realworld.backend.domain.model.comment.Comment;

public interface CommentRepository {
  List<Comment> findByArticleId(long articleId);
  void deleteByArticleId(long articleId);
}
