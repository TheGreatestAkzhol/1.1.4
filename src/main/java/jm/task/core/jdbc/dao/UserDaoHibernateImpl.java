package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS User "+
                "(ID INT PRIMARY KEY AUTO_INCREMENT, "+
                  "name VARCHAR (45), "+
                  "lastname VARCHAR(45), "+
                      "age INT (3))").executeUpdate();
        transaction.commit();
        LOGGER.log(Level.INFO,"We've successfully created our table named user");
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS `user`").executeUpdate();
        transaction.commit();
        LOGGER.log(Level.INFO,"We've successfully deleted our table");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name,lastName,age));
        transaction.commit();
        LOGGER.log(Level.INFO,"We've added user {0} {1} to your table",new Object[]{name,lastName});
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class,id);
        session.delete(user);
        transaction.commit();
        LOGGER.log(Level.INFO,"We've removed user {0} {1} to your table",new Object[]{user.getName(),user.getLastName()});
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        transaction.commit();
        LOGGER.log(Level.INFO,"We've got all users from table");
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String s = "TRUNCATE TABLE user";
        Session session = Util.getConnection().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(s).executeUpdate();
        transaction.commit();
        LOGGER.log(Level.INFO,"We've deleted all users from table");
        session.close();
    }
}
