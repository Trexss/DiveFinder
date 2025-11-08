package com.divefinder.repositories;

import com.divefinder.models.Comment;

import java.util.Set;

public interface CommentRepository {
    Set<Comment> getCommentsForDiveSite(int diveSiteId);

    void addCommentToDiveSite(Comment comment);
}
