package rbit.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;
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
            String tags = "";
            for (int i = 0; i < arrangement.tags.length(); i++) {
                if (arrangement.tags.charAt(i) == '\n' || arrangement.tags.charAt(i) == '\r' || arrangement.tags.charAt(i) == '\t') {
                    tags += " ";
                } else {
                    tags += arrangement.tags.charAt(i);
                }
            }
            statement.setString(4, " " + tags + " ");
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
            String tags = "";
            for (int i = 0; i < arrangement.tags.length(); i++) {
                if (arrangement.tags.charAt(i) == '\n' || arrangement.tags.charAt(i) == '\r' || arrangement.tags.charAt(i) == '\t') {
                    tags += " ";
                } else {
                    tags += arrangement.tags.charAt(i);
                }
            }
            statement.setString(3, " " + tags + " ");
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
    public static Vector<SearchResult> search(Vector<String> tags) {
        Vector<SearchResult> result = new Vector<>();
        try {
            String query = "SELECT * FROM ARRANGEMENTS WHERE ";
            for (int i = 0; i < tags.size(); i++) {
                if (i != 0) query += " or ";
                query += "tags like ?";
            }
            PreparedStatement statement = conn.prepareStatement(query);
            for (int i = 0; i < tags.size(); i++) {
                statement.setString(i+1, "% " + tags.get(i) + " %");
            }
            rs = statement.executeQuery();
            while (rs.next()) {
                result.add(new SearchResult(rs.getString("path"), rs.getString("title"), rs.getString("description"), rs.getString("tags")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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