package hw2.dao;

import hw2.models.User;
import hw2.utils.HibernateExceptionHandler;
import hw2.utils.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UserDAO {
//    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    static Logger logger;
    static {
        try (InputStream ins = HibernateExceptionHandler.class.getClassLoader().getResourceAsStream("log.config")) {
            if (ins != null) {
                LogManager.getLogManager().readConfiguration(ins);
            }
            logger = Logger.getLogger(HibernateExceptionHandler.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public User findById(int id) {
        try(Session session = HibernateSessionFactory.getSession()) {
            return session.find(User.class, id);
        }
    }

    public void create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Объект User не может быть null");
        }

        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.log(Level.INFO, "Создан пользователь: {0}", user.getId());
        }catch (Exception e) {
            HibernateExceptionHandler.handle(e, transaction);
        }
    }

    public void update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Объект User не может быть null");
        }

        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            logger.log(Level.INFO, "Обновлен пользователь: {0}", user.getId());
        }catch (Exception e) {
            HibernateExceptionHandler.handle(e, transaction);
        }

    }

    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Объект User не может быть null");
        }

        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            logger.log(Level.INFO, "Пользователь удален: {0}", user.getId());
        }catch (Exception e) {
            HibernateExceptionHandler.handle(e, transaction);
        }
    }

    public List<User> findAll() {
        try(Session session = HibernateSessionFactory.getSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }
}
