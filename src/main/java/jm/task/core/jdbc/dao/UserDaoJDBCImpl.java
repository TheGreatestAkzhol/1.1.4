package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private PreparedStatement preparedStatement;
    private final String insertNew = "INSERT INTO mydbtest.user(name,lastname,age) VALUES(?,?,?)";
    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
    }

    public void dropUsersTable() {
    }

    public void saveUser(String name, String lastName, byte age) {
    }

    public void removeUserById(long id) {
    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {
    }
    }
