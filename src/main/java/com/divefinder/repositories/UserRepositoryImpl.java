package com.divefinder.repositories;

import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, id);
            if (user == null) {
                throw new com.exceptions.EntityNotFoundException("User ", id);
            }
            return user;

    }

    @Override
    public User findUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User u where u.email = :email", User.class)
                .setParameter("email", email);
        return query.uniqueResultOptional()
                .orElseThrow(() -> new com.exceptions.EntityNotFoundException("User with email " + email + " not found."));
    }


    @Override
    public User createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }
}
