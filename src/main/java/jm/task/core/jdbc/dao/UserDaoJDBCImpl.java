package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "CREATE TABLE IF NOT EXISTS Users" +
                    "(id bigint not null auto_increment," +
                    " name VARCHAR(100), " +
                    "lastname VARCHAR(100), " +
                    "age tinyint, " +
                    "PRIMARY KEY (id))";
            statement.executeUpdate(SQL);
            System.out.println("Таблица успешно создана!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "Drop table if exists Users";
            statement.executeUpdate(SQL);
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO testdb.Users(name,lastName,age) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DELETE FROM Users where id";
            statement.executeUpdate(SQL);
            System.out.println("Пользователь удален!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String SQL = "SELECT id, name, lastName, age from Users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String SQL = "DELETE FROM Users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
            System.out.println("Таблица очищена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
