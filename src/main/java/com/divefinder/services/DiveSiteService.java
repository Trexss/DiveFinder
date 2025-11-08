package com.divefinder.services;

import com.divefinder.models.Comment;
import com.divefinder.models.DiveSite;

import java.util.List;
import java.util.Set;

public interface DiveSiteService {
    List<DiveSite> getAllApprovedSites();
    DiveSite getSiteById(int id);
    DiveSite createDiveSite(DiveSite diveSite);
    void deleteDiveSite(int id);
    Set<Comment> getCommentsForDiveSite(int diveSiteId);

    void addCommentToDiveSite(Comment comment);
}
