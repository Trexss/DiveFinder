package com.divefinder.services;

import com.divefinder.models.Comment;

import java.util.Set;

public interface CommentService {
    Set<Comment> getCommentsForDiveSite(int diveSiteId);

    void addCommentToDiveSite(Comment comment);
}
