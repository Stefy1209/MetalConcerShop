package repository.user;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import user.User;
import utils.HibernateUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserHibernateRepository implements  UserRepository {
    @Override
    public Optional<User> findByUsername(String username) {
        Transaction transaction = null;
        Optional<User> user = Optional.empty();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM User WHERE username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            user = Optional.of(query.uniqueResult()); // use uniqueResult() if you expect one match

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Optional<User> findOne(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        Transaction transaction = null;
        List<User> users = List.of();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // HQL: from User maps to SELECT * FROM users
            users = session.createQuery("FROM User", User.class).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        Transaction transaction = null;
        Optional<User> user = Optional.empty();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            user = Optional.of(entity);
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Optional<User> delete(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();
    }
}
