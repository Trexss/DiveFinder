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
        try(Session session = sessionFactory.openSession()){
            User user = session.get(User.class, id);
            if(user == null){
                throw new com.exceptions.EntityNotFoundException("User ", id);
            }
            return user;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query <User> query = session.createQuery("from User u where u.email = :email", User.class)
                    .setParameter("email", email);
            return query.uniqueResultOptional()
                    .orElseThrow(() -> new com.exceptions.EntityNotFoundException("User with email " + email + " not found."));
        }
    }

    @Override
    public User createUser(User user) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        }
    }
}
