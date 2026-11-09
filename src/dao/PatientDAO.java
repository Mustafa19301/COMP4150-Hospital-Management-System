package dao;

import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatientDAO {

    public List<Map<String, Object>> getAllPatients()
            throws SQLException {

        String sql = "CALL GetAllPatients()";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            return readRows(rs);
        }
    }

    public Map<String, Object> getPatientById(int patientId)
            throws SQLException {

        String sql = "CALL GetPatientByID(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, Object>> patients = readRows(rs);

                return patients.isEmpty() ? null : patients.get(0);
            }
        }
    }

    public List<Map<String, Object>> searchPatientsByName(String name)
            throws SQLException {

        String sql = "CALL SearchPatientsByName(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return readRows(rs);
            }
        }
    }

    public List<Map<String, Object>> getPatientsWithoutMedicalRecords()
            throws SQLException {

        String sql = "CALL GetPatientsWithoutMedicalRecords()";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            return readRows(rs);
        }
    }

    public int addPatient(
            String name,
            LocalDate dateOfBirth,
            String phone,
            String address) throws SQLException {

        String sql = "CALL AddPatient(?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, name);
            stmt.setDate(2, Date.valueOf(dateOfBirth));
            stmt.setString(3, phone);
            stmt.setString(4, address);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("new_patient_id");
                }
            }
        }

        throw new SQLException("Patient could not be added.");
    }

    public boolean updatePatient(
            int patientId,
            String name,
            LocalDate dateOfBirth,
            String phone,
            String address) throws SQLException {

        String sql = "CALL UpdatePatient(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, patientId);
            stmt.setString(2, name);
            stmt.setDate(3, Date.valueOf(dateOfBirth));
            stmt.setString(4, phone);
            stmt.setString(5, address);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("rows_updated") > 0;
            }
        }
    }

    public boolean deletePatient(int patientId)
            throws SQLException {

        String sql = "CALL DeletePatient(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("rows_deleted") > 0;
            }
        }
    }

    private List<Map<String, Object>> readRows(ResultSet rs)
            throws SQLException {

        List<Map<String, Object>> rows = new ArrayList<>();

        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (rs.next()) {

            Map<String, Object> row = new LinkedHashMap<>();

            for (int i = 1; i <= columnCount; i++) {

                Object value = rs.getObject(i);

                if (value instanceof Date) {
                    value = ((Date) value).toLocalDate();
                }

                row.put(metadata.getColumnLabel(i), value);
            }

            rows.add(row);
        }

        return rows;
    }
}