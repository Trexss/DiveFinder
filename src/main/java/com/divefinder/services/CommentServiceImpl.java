package com.divefinder.services;

import com.divefinder.models.Comment;
import com.divefinder.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Set<Comment> getCommentsForDiveSite(int diveSiteId) {
        return commentRepository.getCommentsForDiveSite(diveSiteId);
    }

    @Override
    public void addCommentToDiveSite(Comment comment) {
        commentRepository.addCommentToDiveSite(comment);
    }
}
