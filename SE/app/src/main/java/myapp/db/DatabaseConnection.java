package myapp.db;

import java.sql.*;
import java.util.Properties;

//This is a singleton class which will return a connection object

public class DatabaseConnection {

    private static Connection conn = null;
    private static DatabaseConnection instance = null;

    private DatabaseConnection() {
        String url = "jdbc:postgresql://localhost:5432/aims";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password", "******");
        try{
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
       
    public static Connection connectToServer(){
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return conn;    
    }

}


