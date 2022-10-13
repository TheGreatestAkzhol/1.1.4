package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
      Session session = Util.getSessionFactory().openSession();
    }
}
