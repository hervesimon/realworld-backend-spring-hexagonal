package io.realworld.backend.domain.service.impl;

import java.util.List;

import io.realworld.backend.domain.model.comment.Comment;
import io.realworld.backend.domain.service.CommentService;

public class CommentImplService implements CommentService {
    @Override
    public List<Comment> findByArticleId(long articleId) {
        return null;
    }

    @Override
    public void deleteByArticleId(long articleId) {

    }
}
