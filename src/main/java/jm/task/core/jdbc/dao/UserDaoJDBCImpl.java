package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.dao.UserDao;
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
        String creating = "CREATE TABLE IF NOT EXISTS user" +
                "(ID INTEGER not NULL AUTO_INCREMENT," +
                "name VARCHAR(45) not null," +
                "lastname VARCHAR(45) not null," +
                "age INTEGER NULL," +
                "PRIMARY KEY ( ID )," +
                "UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(creating);
            Object name = "user";
            LOGGER.log(Level.INFO,"You've created table");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql =  "DROP TABLE IF EXISTS `user`";;
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(sql);
            LOGGER.log(Level.INFO, "Table deleted in given database...");
            statement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = Util.getConnection().prepareStatement(insertNew);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.execute();
            preparedStatement.close();
            LOGGER.log(Level.INFO,"User named {0} {1} is added to your database",new Object[]{name,lastName});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String deleting = "delete from user where id = {0}";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(MessageFormat.format(deleting,id));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String getAll = "Select * from mydbtest.user";
        try {
            PreparedStatement ps = Util.getConnection().prepareStatement(getAll);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setId((long) resultSet.getInt("ID"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            String deleteUsers = "truncate table mydbtest.user";
            Statement statement = Util.getConnection().createStatement();
            statement.execute(deleteUsers);
            LOGGER.log(Level.INFO,"All users deleted from your database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}