package dao;

import data.JDBCConnection;
import model.Card;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardDAO implements IDAO<Card> {
    private String tableName = "card";
    private String userName = "userName";
    private String passWord = "passWord";
    private String balance = "balance";
    public static CardDAO getInstance(){
        return new CardDAO();
    }

    @Override
    public void insert(Card card) {
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "INSERT INTO %s(%s, %s, %s)VALUES (?, ?, ?)".
                    formatted(tableName, userName, passWord, balance);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getUserName());
            preparedStatement.setString(2, card.getPassWord());
            preparedStatement.setFloat(3, card.getBalance());
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
    public void update(Card card) {
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;".
                    formatted(tableName, passWord, balance, userName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getPassWord());
            preparedStatement.setFloat(2, card.getBalance());
            preparedStatement.setString(3, card.getUserName());
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
    public ArrayList<Card> selectAll() {
        ArrayList<Card> cardArrayList = new ArrayList<Card>();
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s;".formatted(tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String passWord = resultSet.getString(this.passWord);
                float balance = resultSet.getFloat(this.balance);
                Card card = new Card(userName, passWord, balance);
                cardArrayList.add(card);
            }
            if (cardArrayList != null) {
                System.out.println("select successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardArrayList;
    }

    @Override
    public Card selectById(String id) {
        Card card = null;
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s WHERE %s = ?;".formatted(tableName, userName);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String passWord = resultSet.getString(this.passWord);
                float balance = resultSet.getFloat(this.balance);
                card = new Card(userName, passWord, balance);
            }
            if (card != null) {
                System.out.println("select by id successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public ArrayList<Card> selectByCondition(String condition) {
        ArrayList<Card> cardArrayList = new ArrayList<Card>();
        try {
            Connection connection = JDBCConnection.getConnection();
            String sql = "SELECT * FROM %s WHERE %s;".formatted(tableName, condition);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString(this.userName);
                String passWord = resultSet.getString(this.passWord);
                float balance = resultSet.getFloat(this.balance);
                Card card = new Card(userName, passWord, balance);
                cardArrayList.add(card);
            }
            if (cardArrayList != null) {
                System.out.println("select by condition successfully!");
            }
            JDBCConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardArrayList;
    }
}
