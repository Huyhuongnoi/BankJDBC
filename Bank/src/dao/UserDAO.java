package dao;

import data.JDBCConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IDAO<User> {
    private String tableName = "user";
    private String userName = "userName";
    private String fullName = "fullName";
    private String age = "age";
    private String sex = "sex";
    private String address = "address";
    public static UserDAO getInstance(){
        return new UserDAO();
    }

    @Override
    public void insert(User user) {
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "INSERT INTO %s(%s, %s, %s, %s, %s)VALUES (?, ?, ?, ?, ?)".
                    formatted(tableName, userName, fullName, age, sex, address);
            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getSex());
            preparedStatement.setString(5, user.getAddress());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("added successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;".
                    formatted(tableName, fullName, age, sex, address, userName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getUserName());
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("update successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "DELETE FROM %s WHERE %s = ?;".formatted(tableName, userName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                System.out.println("delete successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> userArrayList = new ArrayList<User>();
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s;".formatted(tableName);
            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                User user = new User(userName, fullName, age, sex, address);
                userArrayList.add(user);
            }
            if (userArrayList != null) {
                System.out.println("select successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    @Override
    public User selectById(String id) {
        User user = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s WHERE %s = ?;".formatted(tableName, userName);
            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                user = new User(userName, fullName, age, sex, address);

            }
            if (user != null) {
                System.out.println("select by id successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public ArrayList<User> selectByCondition(String condition) {
        ArrayList<User> userArrayList = new ArrayList<User>();
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s WHERE %s;".formatted(tableName, condition);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String fullName = resultSet.getString(this.fullName);
                int age = resultSet.getInt(this.age);
                String sex = resultSet.getString(this.sex);
                String address = resultSet.getString(this.address);
                User user = new User(userName, fullName, age, sex, address);
                userArrayList.add(user);
            }
            if (userArrayList != null) {
                System.out.println("select by condition successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }
}
