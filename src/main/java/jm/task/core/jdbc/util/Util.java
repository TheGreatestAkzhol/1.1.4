package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class Util {
        private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
        private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "root";

        public static Connection getConnection() {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
                if (!connection.isClosed()) {
                    LOGGER.log(Level.INFO, "We've connected with database succesfully!)");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.log(Level.INFO, "Unfortunately we've not connected");
            }
            return connection;
        }

        private static SessionFactory sessionFactory;//настройка и работа с сессиями (фабрика сессии)

        public static SessionFactory getSessionFactory() {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url", HOST + "?useSSL=false")
                        .setProperty("hibernate.connection.username", USERNAME)
                        .setProperty("hibernate.connection.password",PASSWORD)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                        .setProperty("hibernate.connection.characterEncoding", "utf8")
                        .setProperty("hibernate.show_sql", "true")
                        .setProperty("hibernate.format_sql", "true")
                        .setProperty("hibernate.default_schema", "mydbtest")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("hibernate.current_session_context_class", "thread")
                        .addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                LOGGER.log(Level.INFO, "We've received the SessionFactory");
            } catch (HibernateException e) {
                throw new RuntimeException(e);
            }
            return sessionFactory;
        }
    }