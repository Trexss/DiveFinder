package com.divefinder.services;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.models.DiveSite;
import com.divefinder.repositories.DiveSiteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiveSiteServiceImpl implements DiveSiteService {
    private final DiveSiteRepository diveSiteRepository;

    public DiveSiteServiceImpl(DiveSiteRepository diveSiteRepository) {
        this.diveSiteRepository = diveSiteRepository;
    }
    @Override
    public List<DiveSite> getAllApprovedSites() {
        return diveSiteRepository.getAllApprovedSites();
    }

    @Override
    public DiveSite getSiteById(int id) {
        return diveSiteRepository.getSiteById(id);
    }

    @Override
    public DiveSite createDiveSite(DiveSite diveSite) {
        try {
            // If a site with the same name exists, getDiveSiteByName will succeed -> duplicate
            diveSiteRepository.getDiveSiteByName(diveSite.getSiteName());
            throw new EntityDuplicateException("Dive site with name " + diveSite.getSiteName() + " already exists.");
        } catch (com.exceptions.EntityNotFoundException e) {
            // Not found -> create and return
            return diveSiteRepository.createDiveSite(diveSite);
        }
    }

    @Override
    public void deleteDiveSite(int id) {

    }
}
