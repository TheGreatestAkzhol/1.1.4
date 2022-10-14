package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Akzhol","Serikbek",(byte)22);
        us.saveUser("Miron","Yanovich",(byte)22);
        us.saveUser("Jonny","Sins",(byte)22);
        us.getAllUsers().stream().forEach((c) -> System.out.println(c));
        us.cleanUsersTable();
        us.dropUsersTable();

    }
}
