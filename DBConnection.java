import java.sql.*;
import java.util.Scanner;

public class DBConnection {
    private Connection con;
    private Scanner sc;

    public void connect() {
        sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/";
        String username = "****";
        String password = "****";
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failed: Check your username and password.");
        }
    }

    public Connection getConnection() {
        return con;
    }

    public Scanner getScanner() {
        return sc;
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection.");
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
