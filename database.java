import java.sql.*;
//import java.io.*;
//import java.sql.SQLException;

public class database {
    private final static String className = "oracle.jdbc.driver.OracleDriver";
    private final static String url = "jdbc:postgresql://localhost:5432/armaan";
    private final static String user = "postgres";
    private final static String password = "armaan1011";
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

//            } catch (ClassNotFoundException ex) {
//                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Opened database successfully");
        //Statement st = connection.createStatement();
        return st;
    }
}
