package rbit.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import rbit.arrangement.Arrangement;

public class DataBase {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/RBit?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void rebuild() {
        try {
            String drop = "DROP TABLE ARRANGEMENTS";
            stmt.executeUpdate(drop);
        } catch (Exception e) {
        }
        String query = "CREATE TABLE ARRANGEMENTS" +
                        "(path varchar(255)," +
                        "title varchar(255)," +
                        "description varchar(255)," +
                        "tags varchar(255)," +
                        "PRIMARY KEY (path))";
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insert(String path, Arrangement arrangement) {
        String query = "INSERT INTO ARRANGEMENTS (path, title, description, tags) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, path);
            statement.setString(2, arrangement.title);
            statement.setString(3, arrangement.description);
            statement.setString(4, arrangement.tags);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void update(String path, Arrangement arrangement) {
        String query = "UPDATE ARRANGEMENTS SET title = ?, description = ?, tags = ? WHERE path = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, arrangement.title);
            statement.setString(2, arrangement.description);
            statement.setString(3, arrangement.tags);
            statement.setString(4, path);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delete(String path) {
        String query = "DELETE FROM ARRANGEMENTS WHERE path = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, path);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveFile(String path, Arrangement arrangement) {
        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(arrangement);
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Arrangement getArrangement(File file) {
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);
            Arrangement ret = (Arrangement)o.readObject();
            o.close();
            f.close();
            return ret;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Arrangement getArrangement(String path) {
        return getArrangement(new File(path));
    }
}