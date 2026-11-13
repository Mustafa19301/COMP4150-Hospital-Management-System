package main;

import dao.AuthDAO;

import java.sql.SQLException;
import java.util.Map;

public class AuthTest {

    public static void main(String[] args) {

        AuthDAO authDAO = new AuthDAO();

        testLogin(authDAO, "jwilson", "doctor4");

        testLogin(authDAO, "ebrown", "nurse4");

        testLogin(authDAO, "otaylor", "pharmacy1");

        testLogin(authDAO, "jwilson", "wrongpassword");
    }

    private static void testLogin(AuthDAO authDAO, String username, String password) {

        try {
            Map<String, Object> staff = authDAO.login(username, password);

            if (staff != null) {
                System.out.println("LOGIN SUCCESSFUL");
                System.out.println("Staff ID: " + staff.get("staff_id"));
                System.out.println("Name: " + staff.get("name"));
                System.out.println("Role: " + staff.get("role"));
            } else {
                System.out.println("LOGIN FAILED: Invalid credentials for " + username);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        System.out.println("\n");
    }
}