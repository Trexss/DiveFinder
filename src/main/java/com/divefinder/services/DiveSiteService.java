package com.divefinder.services;

import com.divefinder.models.DiveSite;

import java.util.List;

public interface DiveSiteService {
    List<DiveSite> getAllApprovedSites();
    DiveSite getSiteById(int id);
    DiveSite createDiveSite(DiveSite diveSite);
    void deleteDiveSite(int id);
}
