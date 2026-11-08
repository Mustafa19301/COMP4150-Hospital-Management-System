
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/hospital_management";

    private static final String USER = "root";

    
    private static final String PASSWORD = "password";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
        );
    }

    public static void main(String[] args) {

        try (Connection connection = getConnection()) {

            System.out.println(
                "Successfully connected to hospital_management!"
            );

        } catch (SQLException e) {

            System.out.println(
                "Database connection failed: " + e.getMessage()
            );
        }
    }
}
