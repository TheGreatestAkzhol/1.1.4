package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static SessionFactory sessionFactory;//настройка и работа с сессиями (фабрика сессии)
    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()//получение реестра сервисов
                .configure()//настройка из hibernate.cfg.xml в скобках можно указать путь
                .build();
        try {
            //MetadataSources - для работы с метаданными маппинга обьектов
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }catch(Exception e){
            //The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            //so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    public static SessionFactory getSessionFactory(){return sessionFactory;}
}
