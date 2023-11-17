package io.realworld.backend.domain.service;

import java.util.List;

import io.realworld.backend.domain.model.comment.Comment;

public interface CommentService {
    List<Comment> findByArticleId(long articleId);
    void deleteByArticleId(long articleId);
}
