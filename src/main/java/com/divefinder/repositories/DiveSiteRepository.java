package com.divefinder.repositories;

import com.divefinder.models.DiveSite;

import java.util.List;

public interface DiveSiteRepository {
    List<DiveSite> getAllApprovedSites();
    DiveSite getSiteById(int id);
    DiveSite createDiveSite(DiveSite diveSite);
    void deleteDiveSite(int id);
    DiveSite getDiveSiteByName(String name);
}
