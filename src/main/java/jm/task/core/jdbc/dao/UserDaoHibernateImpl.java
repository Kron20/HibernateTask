package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Statement;
import java.util.List;

import static jm.task.core.jdbc.util.Util.HibernateSessionFactoryUtil.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String SQL = "CREATE TABLE IF NOT EXISTS User" +
                "(id bigint not null auto_increment," +
                " name VARCHAR(100), " +
                "lastname VARCHAR(100), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";

        Query query = session.createSQLQuery(SQL).addEntity(User.class);
        query.executeUpdate();
        System.out.println("Таблица успешно создана!");
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String SQL = "Drop table if exists User";
        Query query = session.createSQLQuery(SQL).addEntity(User.class);
        query.executeUpdate();
        System.out.println("Таблица удалена!");
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        tx.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User userWithThisId = session.get(User.class, id);
        session.remove(userWithThisId);
        tx.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();


        session.createQuery("DELETE FROM User").executeUpdate();
        System.out.println("Таблица очищена!");
        transaction.commit();
        session.close();
    }
}
