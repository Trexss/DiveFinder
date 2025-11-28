package com.divefinder.services;

import com.divefinder.models.Comment;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;

import java.util.List;
import java.util.Set;

public interface DiveSiteService {
    List<DiveSite> getAllApprovedSites();
    DiveSite getSiteById(int id);
    DiveSite createDiveSite(DiveSite diveSite);
    void deleteDiveSite(int id, User user);
    Set<Comment> getCommentsForDiveSite(int diveSiteId);

    void addCommentToDiveSite(Comment comment);

    DiveSite getApprovedSiteById(int id);
}
