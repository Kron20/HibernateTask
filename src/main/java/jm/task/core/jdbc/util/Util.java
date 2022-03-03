package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.mapping.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String dbURL = "jdbc:mysql://127.0.0.1:3306/testdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "markiz12";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public class HibernateSessionFactoryUtil {
        private static SessionFactory sessionFactory;

        private HibernateSessionFactoryUtil() {
        }

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();
                    Properties settings = new Properties();
                    settings.put(Environment.URL, dbURL);
                    settings.put(Environment.USER, USERNAME);
                    settings.put(Environment.PASS, PASSWORD);
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);
                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());

                } catch (Exception e) {
                    System.out.println("Исключение!" + e);
                }
            }
            return sessionFactory;
        }
    }
}// реализуйте настройку соеденения с БД

