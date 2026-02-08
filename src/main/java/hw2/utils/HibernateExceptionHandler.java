package hw2.utils;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.hibernate.Transaction;

public class HibernateExceptionHandler {
//    private static final Logger logger = Logger.getLogger(HibernateExceptionHandler.class.getName());
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

    public static void handle(Exception e, Transaction transaction) {
        logger.log(Level.SEVERE, "Произошла ошибка: " + e.getMessage(), e);

        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
                logger.log(Level.WARNING, "Транзакция отменена из за ошибки");
            } catch (Exception rollbackEx) {
                logger.log(Level.SEVERE, "Не удалось отменить транзакцию: " + rollbackEx.getMessage(), rollbackEx);
            }
        }
        throw new RuntimeException("Ошибка операции БД", e);
    }

    public static void handle(Exception e) {
        handle(e, null);
    }
}
