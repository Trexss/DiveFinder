package com.divefinder.repositories;

import com.divefinder.models.DiveSite;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class DiveSiteRepositoryImpl implements DiveSiteRepository{

    private final SessionFactory sessionFactory;
    @Autowired
    public DiveSiteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<DiveSite> getAllApprovedSites() {
        Session session = sessionFactory.getCurrentSession();
            Query<DiveSite> query = session.createQuery("from DiveSite where isApproved = true", DiveSite.class);
            return query.list();

    }

    @Override
    public DiveSite getSiteById(int id) {
        Session session = sessionFactory.getCurrentSession();
            DiveSite diveSite = session.get(DiveSite.class, id);
            if(diveSite == null){
                throw new com.exceptions.EntityNotFoundException("Dive site ", id);
            }
            return diveSite;

    }

    @Override
    public DiveSite createDiveSite(DiveSite diveSite) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(diveSite);
        return diveSite;
    }

    @Override
    public void deleteDiveSite(int id) {
        Session session = sessionFactory.getCurrentSession();
        DiveSite siteToDelete = session.get(DiveSite.class, id);
        session.remove(siteToDelete);
    }

    @Override
    public DiveSite getDiveSiteByName(String name) {
        Session session = sessionFactory.getCurrentSession();
            Query<DiveSite> query = session.createQuery("from DiveSite where siteName = :name", DiveSite.class);
            query.setParameter("name", name);
            DiveSite diveSite = query.uniqueResult();
            if(diveSite == null){
                throw new com.exceptions.EntityNotFoundException("Dive Site with " + name + "does not exist");
            }
            return diveSite;

    }

    @Override
    public List<DiveSite> getAllUnapprovedSites() {
        Session session = sessionFactory.getCurrentSession();
            Query<DiveSite> query = session.createQuery("from DiveSite where isApproved = false", DiveSite.class);
            return query.list();
    }

    @Override
    public void updateDiveSite(DiveSite diveSite) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(diveSite);
    }
}
