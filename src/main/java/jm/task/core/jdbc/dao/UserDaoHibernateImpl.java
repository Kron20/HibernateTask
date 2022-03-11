package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    static Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS User" +
                "(id bigint not null auto_increment," +
                " name VARCHAR(100), " +
                "lastname VARCHAR(100), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(SQL).addEntity(User.class);
            query.executeUpdate();
            System.out.println("Таблица успешно создана!");
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String SQL = "Drop table if exists User";
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(SQL).addEntity(User.class);
            query.executeUpdate();
            System.out.println("Таблица удалена!");
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            User userWithThisId = session.get(User.class, id);
            session.remove(userWithThisId);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            users = session.createQuery("From User").list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            System.out.println("Таблица очищена!");
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
