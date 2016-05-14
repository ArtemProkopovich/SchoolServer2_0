package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Артем on 01.05.2016.
 */
public class MySqlConnection {
    private Connection connection;
    private String USER = "root";
    private String PASSWORD = "root";
    private String HOST = "jdbc:mysql://localhost:3306/school_db";

    public MySqlConnection(){}
    public MySqlConnection(String host, String user, String password){
        this.USER = user;
        this.PASSWORD = password;
        this.HOST = host;
    }

    public Connection getConnection(){
        if (connection != null)
            return connection;
        else
            try {
                connection = DriverManager.getConnection(HOST, USER, PASSWORD);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void closeConnection(){
        if (connection!=null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
