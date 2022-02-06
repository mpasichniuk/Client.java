package server.homework;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface AuthService {
   Connection connection = null;
  Statement statement = null;

    static void connection() {
        try {
            Class.forName("org.sqlite.JDBC");
           Connection connection = DriverManager.getConnection("jdbc:sqlite:Users/mariapasichniuk/Downloads/main.db");
            Statement statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNewUsers(String login, String pass, String nick) {
        connection();
        int hash = pass.hashCode();
        String sql = String.format("INSERT INTO users(login, password) VALUES ('%s', '%d', )", login, pass);

        try {
            boolean rs = statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    static int getIdByNick(String username) {
        String idUsername = String.format("SELECT id FROM users where username= '%s'", username);
        try {
            ResultSet rs = statement.executeQuery(idUsername);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static String getNickByLoginAndPass(String login, String pass) {
        String sql = String.format("SELECT username FROM users where username = '%s' and password = '%s'", login, pass);

        try {
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                String str = rs.getString(1);
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void changeNickname(String oldUsername, String newUsername) {
        connection();
        try {
            statement.execute("UPDATE users set username=" + newUsername + "WHERE username=" + oldUsername);
        } catch (SQLException e) {
            System.out.println("Ошибка обновления ника");
     } finally {
           disconnect();
        }
  }

}

