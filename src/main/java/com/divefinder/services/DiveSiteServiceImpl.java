package com.divefinder.services;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.models.Comment;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import com.divefinder.repositories.DiveSiteRepository;

import com.exceptions.AuthorizationException;
import com.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DiveSiteServiceImpl implements DiveSiteService {
    private final DiveSiteRepository diveSiteRepository;
    private final CommentService commentService;

    public DiveSiteServiceImpl(DiveSiteRepository diveSiteRepository, CommentService commentService) {
        this.diveSiteRepository = diveSiteRepository;
        this.commentService = commentService;
    }
    @Override
    @Transactional(readOnly = true)
    public List<DiveSite> getAllApprovedSites() {
        return diveSiteRepository.getAllApprovedSites();
    }

    @Override
    public DiveSite getSiteById(int id) {
        return diveSiteRepository.getSiteById(id);
    }


    @Override
    @Transactional
    public DiveSite createDiveSite(DiveSite diveSite) {
        try {
            // checks if DiveSite with the same name exists
            diveSiteRepository.getDiveSiteByName(diveSite.getSiteName());
            throw new EntityDuplicateException("Dive site with name " + diveSite.getSiteName() + " already exists.");
        } catch (com.exceptions.EntityNotFoundException e) {

            return diveSiteRepository.createDiveSite(diveSite);
        }
    }

    @Override
    @Transactional
    public void deleteDiveSite(int id, User user) {
        if (!user.isAdmin()){
            throw new AuthorizationException("Only admins are authorized to delete a dive site");
        }
        try {
            // checks if DiveSite exists
            diveSiteRepository.getSiteById(id);
            diveSiteRepository.deleteDiveSite(id);
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new com.exceptions.EntityNotFoundException("Dive site  ", id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Set<Comment> getCommentsForDiveSite(int diveSiteId) {
        try {
            // checks if DiveSite exists
            diveSiteRepository.getSiteById(diveSiteId);
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new com.exceptions.EntityNotFoundException("Dive site  ", diveSiteId);
        }
        return commentService.getCommentsForDiveSite(diveSiteId);
    }

    @Override
    public void addCommentToDiveSite(Comment comment) {
        commentService.addCommentToDiveSite(comment);
    }

    @Override
    public DiveSite getApprovedSiteById(int id) {
        DiveSite diveSite = diveSiteRepository.getSiteById(id);
        if (!diveSite.isApproved()){
            throw new EntityNotFoundException("Dive site ", id);
        }
        return diveSite;
    }

    @Override
    @Transactional
    public List<DiveSite> getAllUnapprovedSites() {
        return diveSiteRepository.getAllUnapprovedSites();
    }

    @Override
    @Transactional
    public void approveDiveSite(int id, User user) {
        if (!user.isAdmin()){
            throw new AuthorizationException("Not authorized to approve dive sites");
        }
        DiveSite diveSite = diveSiteRepository.getSiteById(id);
        diveSite.setApproved(true);
        diveSiteRepository.updateDiveSite(diveSite);
    }
}
