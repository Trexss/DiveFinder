package com.divefinder.services;

import com.divefinder.models.Comment;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import com.divefinder.repositories.CommentRepository;
import com.divefinder.repositories.DiveSiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
private final CommentRepository commentRepository;
private final UserService userService;
private final DiveSiteRepository diveSiteRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, DiveSiteRepository diveSiteRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.diveSiteRepository = diveSiteRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Set<Comment> getCommentsForDiveSite(int diveSiteId) {
        return commentRepository.getCommentsForDiveSite(diveSiteId);
    }

    @Override
    @Transactional
    public void addCommentToDiveSite(Comment comment) {
        User managedUser = userService.findUserById(comment.getUser().getId());
        DiveSite managedSite = diveSiteRepository.getSiteById(comment.getDiveSite().getId());

        if (managedUser == null || managedSite == null) {
            throw new com.exceptions.EntityNotFoundException("User or DiveSite not found");
        }
        comment.setUser(managedUser);
        comment.setDiveSite(managedSite);
        commentRepository.addCommentToDiveSite(comment);
    }
}
