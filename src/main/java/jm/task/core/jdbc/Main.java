package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserServiceImpl userserv = new UserServiceImpl();
    private static final User user1 = new User("Alex", "Hoff", (byte) 25);
    private static final User user2 = new User("Tom", "Smith", (byte) 29);
    private static final User user3 = new User("John", "Fallen", (byte) 21);
    private static final User user4 = new User("Allan", "White", (byte) 34);

    public static void main(String[] args) {
        userserv.createUsersTable();
        userserv.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userserv.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userserv.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userserv.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println(userserv.getAllUsers());
        userserv.cleanUsersTable();
        userserv.dropUsersTable();

        // реализуйте алгоритм здесь
    }
}
