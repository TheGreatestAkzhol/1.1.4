package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class Util {
        private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
        private static final String HOST_KEY = "db.url";
        private static final String USERNAME_KEY = "db.username";
        private static final String PASSWORD_KEY = "db.password";
        private static final Properties PROPERTIES = new Properties();
        private Util(){
        }
        static{
            loadProperties();
        }
        public static String get(String key){
            return PROPERTIES.getProperty(key);
        }
        private static void loadProperties(){
            try (var inputstream = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputstream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public static Connection getConnection() {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(
                        Util.get(HOST_KEY),
                        Util.get(USERNAME_KEY),
                        Util.get(PASSWORD_KEY));
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
                        .setProperty("hibernate.connection.url", Util.get(HOST_KEY) + "?useSSL=false")
                        .setProperty("hibernate.connection.username", Util.get(USERNAME_KEY))
                        .setProperty("hibernate.connection.password",Util.get(PASSWORD_KEY))
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