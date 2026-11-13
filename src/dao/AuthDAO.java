package dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthDAO {

    public Map<String, Object> login(String username, String password)
            throws SQLException {

        String sql = """
                SELECT staff_id, name, role
                FROM Staff
                WHERE username = ?
                AND password_hash = SHA2(?, 256)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    Map<String, Object> staff = new HashMap<>();

                    staff.put("staff_id", result.getInt("staff_id"));
                    staff.put("name", result.getString("name"));
                    staff.put("role", result.getString("role"));

                    return staff;
                }
            }
        }
        return null;
    }
}