package repository.concert;

import concert.Concert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConcertHibernateRepository implements ConcertRepository {
    @Override
    public Optional<Concert> findOne(UUID uuid) {
        Transaction transaction = null;
        Optional<Concert> concert = Optional.empty();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Concert result = session.find(Concert.class, uuid);
            if (result != null) {
                concert = Optional.of(result);
            }

            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return concert;
    }

    @Override
    public Iterable<Concert> findAll() {
        Transaction transaction = null;
        List<Concert> concerts = List.of();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            concerts = session.createQuery("FROM Concert", Concert.class).getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return concerts;
    }

    @Override
    public Optional<Concert> save(Concert entity) {
        Transaction transaction = null;
        Optional<Concert> concert = Optional.empty();

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            concert = Optional.of(entity);
            e.printStackTrace();
        }

        return concert;
    }

    @Override
    public Optional<Concert> delete(UUID uuid) {
        Transaction transaction = null;
        Optional<Concert> concert = findOne(uuid);

        if (concert.isPresent()) {
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();

                session.remove(concert.get());

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        }

        return concert;
    }

    @Override
    public Optional<Concert> update(Concert entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Concert updated = session.merge(entity);
            transaction.commit();

            return Optional.of(updated);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
