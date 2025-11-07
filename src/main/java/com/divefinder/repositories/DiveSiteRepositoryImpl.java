package com.divefinder.repositories;

import com.divefinder.models.DiveSite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiveSiteRepositoryImpl implements DiveSiteRepository{


    @Override
    public List<DiveSite> getAllApprovedSites() {
        return List.of();
    }

    @Override
    public DiveSite getSiteById(int id) {
        return null;
    }

    @Override
    public DiveSite createDiveSite(DiveSite diveSite) {
        return null;
    }

    @Override
    public void deleteDiveSite(int id) {

    }

    @Override
    public DiveSite getDiveSiteByName(String name) {
        return null;
    }
}
