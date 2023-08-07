import java.sql.*;
//import java.io.*;
//import java.sql.SQLException;

public class database {
    private final static String className = "oracle.jdbc.driver.OracleDriver";
    private final static String url = "jdbc:postgresql://localhost:5432/iitrpr";
    private final static String user = "postgres";
    private final static String password = "iitrpr1011";
    private static Connection connection;
    private static Statement st;

    /**
     * Returns the database connection.
     *
     * @return database connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Statement getConnection() {
        if (connection == null) {
            try {
//                Class.forName(className);
                 
                connection = DriverManager.getConnection(url, user, password);
                st = connection.createStatement();
                
                System.out.println("Opened database successfully");
               //Statement st = connection.createStatement();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return st;
    }
}
