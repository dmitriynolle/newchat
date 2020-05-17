package com.geekbrains.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {
    Connection conn;

    public SimpleAuthService() {
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:clientdb.db");
            String string = String.format("SELECT nickname FROM users where login = '%s' and password = '%s'", login, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(string);
            if (resultSet.next()){
                return (resultSet.getString(1));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int changeNickname(String nickName, String newNickName) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:clientdb.db");
            String string = String.format("UPDATE users SET nickname = '%s' where nickname = '%s'", newNickName, nickName);
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(string);
            return result;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }
}
