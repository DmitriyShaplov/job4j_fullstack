package ru.shaplov.rmi.example.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.shaplov.rmi.example.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * CRUD class for Payment.
 *
 * @author shaplov
 * @since 25.09.2019
 */
public class DAOPayment {

    private static final DAOPayment INSTANCE = new DAOPayment();
    private final SessionFactory factory;

    private DAOPayment() {
        factory = HibernateUtil.getSessionFactory();
    }

    public static DAOPayment getInstance() {
        return INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Payment create(Payment payment) {
        return tx(session -> {
            session.save(payment);
            return payment;
        });
    }

    public void changeValue(BigDecimal amount, int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("update Payment p set p.value = p.value + :amount where p.id = :id")
                    .setParameter("amount", amount)
                    .setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Payment> list() {
        Session session = factory.openSession();
        try {
            return session.createQuery("from Payment", Payment.class).list();
        } finally {
            session.close();
        }
    }

    public void delete(Payment payment) {
        tx(session -> {
            session.delete(payment);
            return null;
        });
    }
}
