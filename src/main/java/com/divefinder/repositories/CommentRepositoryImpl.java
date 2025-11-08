package com.divefinder.repositories;

import com.divefinder.models.Comment;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CommentRepositoryImpl implements CommentRepository{
    private final SessionFactory sessionFactory;

    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Comment> getCommentsForDiveSite(int diveSiteId) {
        Session session = sessionFactory.getCurrentSession();
            Query<Comment> query = session.createQuery(
                    "select distinct c from Comment c " +
                            "join fetch c.user u " +
                            "join fetch c.diveSite ds " +
                            "where c.diveSite.id = :diveSiteId",
                    Comment.class
            );
            query.setParameter("diveSiteId", diveSiteId);
            List<Comment> list = query.list();
            return new HashSet<>(list);


    }

    @Override
    public void addCommentToDiveSite(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(comment);
    }
}

